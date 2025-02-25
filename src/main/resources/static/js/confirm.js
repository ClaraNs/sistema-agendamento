document.addEventListener("DOMContentLoaded", () => {
    function formatarDataParaTexto(dataStr, horaStr) {
        const meses = [
            "janeiro", "fevereiro", "março", "abril", "maio", "junho",
            "julho", "agosto", "setembro", "outubro", "novembro", "dezembro"
        ];
    
        // Divide a string da data (formato "dd/MM/yyyy")
        const partesData = dataStr.split("/");
        const dia = partesData[0];
        const mes = meses[parseInt(partesData[1], 10) - 1]; // Pega o mês pelo índice
        //const ano = partesData[2];
    
        return `Dia ${dia} de ${mes} às ${horaStr}`;
    }

    function formatarParaISO(dataStr, horaStr) {
        const partesData = dataStr.split("/");
        const dia = partesData[0];
        const mes = partesData[1];
        const ano = partesData[2];
    
        return `${ano}-${mes}-${dia}T${horaStr}:00`; // yyyy-MM-ddTHH:mm:ss
    }

    const dataSelecionada = localStorage.getItem("dataSelecionada");
    const horarioSelecionado = localStorage.getItem("horarioSelecionado");
    const id = localStorage.getItem("consultaId");
    const dataHoraISO = formatarParaISO(dataSelecionada, horarioSelecionado);
    const cpf = localStorage.getItem("cpf");
    const resultado = formatarDataParaTexto(dataSelecionada, horarioSelecionado);
    const confirmarBtn = document.getElementById("confirmBtn");
    const cancelarBtn = document.getElementById("cancelarBtn");

    if (dataSelecionada && horarioSelecionado) {
        document.getElementById("resumoAgendamento").textContent = resultado;
    } else {
        document.getElementById("resumoAgendamento").textContent = "Nenhuma data selecionada.";
    }

    confirmarBtn.addEventListener( "click", async () => {

        try {

            const response = await fetch(`http://localhost:8080/appointments/${id}`, {
                method: "PATCH",
                headers:{
                    "Content-Type": "application/json"
                }
            });

            if (response.ok) {
                console.log(id);
                console.log("Horário confirmado com sucesso!");
                window.location.href="historico.html"
            } else {
                console.log("Erro ao confirmar. Verifique os dados.");
            }
            
        } catch (error) {
            console.log("Erro ao confirmar. Verifique os dados.");
        }

        
    });

    cancelarBtn.addEventListener("click", async () => {
        const confirmation = confirm("Tem certeza que deseja cancelar o agendamento?");
    
        if (confirmation) {
            try {
                const response = await fetch(`http://localhost:8080/appointments/${id}`, {
                    method: "DELETE",
                    headers: {
                        "Content-Type": "application/json"
                    }
                });
    
                if (response.ok) {
                    console.log("Horário cancelado com sucesso!");
                    window.location.href = "historico.html";
                } else {
                    console.log("Erro ao cancelar. Verifique os dados.");
                }
            } catch (error) {
                console.log("Erro ao cancelar. Verifique os dados.");
            }
        } else {
            console.log("Cancelamento abortado pelo usuário.");
        }
    });
});