document.getElementById("register-btn").addEventListener("click", async function (event) {
    event.preventDefault();

    const nome = document.getElementById("nome").value.trim();
    const cpf = document.getElementById("cpf").value.trim();
    const email = document.getElementById("email").value.trim();
    const telefone = document.getElementById("telefone").value.trim().replace(/[\(\)\-\s]/g, '');
    const dataNascimento = document.getElementById("data_nascimento").value.trim();

    if (!nome || !cpf || !email || !telefone || !dataNascimento) {
        alert("Preencha todos os campos!");
        return;
    }

    const clientData = {
        nome: nome,
        cpf: cpf,
        email: email,
        telefone: telefone,
        data_nascimento: dataNascimento
    };

    try{
        const response = await fetch("http://localhost:8080/clients", {
            method: "POST",
            headers:{
                "Content-Type": "application/json"
            },
            body: JSON.stringify(clientData)
        });

        if (response.ok) {
            console.log("Cadastro realizado com sucesso!");
            window.location.href = "index.html";
        } else {
            console.log("Erro ao cadastrar. Verifique os dados.");
        }

    }catch(error){
        console.log("Erro ao enviar cadastro:", error);
    }
    
})  
