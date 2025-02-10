document.getElementById("login-btn").addEventListener("click", async function(event){
    event.preventDefault();  // Impede o envio do formulário tradicional

    let cpf = document.getElementById("cpf").value.trim();
    
    if(!cpf){
        return;
    }

    cpf = cpf.replace(/[.\-]/g, '');
    console.log("CPF:", cpf);

    try {
        const response = await fetch(`http://localhost:8080/clients/${cpf}`);
        
        if (response.ok) {
            localStorage.setItem("cpf", cpf);
            window.location.href = "index.html";
        } else if (response.status === 404) {
            window.location.href = "cadastro.html";
        }
    } catch (error) {
        console.log("Erro ao fazer requisição:", error);
    }
});
