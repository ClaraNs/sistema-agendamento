document.getElementById("login-btn").addEventListener("click", async function(event){
    event.preventDefault();  // Impede o envio do formulário tradicional

    let cpf = document.getElementById("cpf").value.trim();
    
    if(!cpf){
        return;
    }

    cpf = cpf.replace(/[.\-]/g, '');
    console.log("CPF:", cpf);

    // Aqui você pode adicionar a requisição para a API, por exemplo
    try {
        const response = await fetch(`http://localhost:8080/clients/${cpf}`);
        
        if (response.ok) {
            window.location.href = "index.html";  // Redireciona para a página inicial
        } else if (response.status === 404) {
            window.location.href = "cadastro.html";  // Redireciona para a página de cadastro
        }
    } catch (error) {
        console.log("Erro ao fazer requisição:", error);
    }
});
