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
    const dataHoraISO = formatarParaISO(dataSelecionada, horarioSelecionado);
    const cpf = localStorage.getItem("cpf");
    const resultado = formatarDataParaTexto(dataSelecionada, horarioSelecionado);
    const agendarBtn = document.getElementById("agendarBtn");

    if (dataSelecionada && horarioSelecionado) {
        document.getElementById("resumoAgendamento").textContent = resultado;
    } else {
        document.getElementById("resumoAgendamento").textContent = "Nenhuma data selecionada.";
    }

    agendarBtn.addEventListener( "click", async () => {

        // criar agendamento
        const appointmentData = {
            data: dataHoraISO,
            paciente_cpf: cpf
        }

        try {

            const response = await fetch("http://localhost:8080/appointments", {
                method: "POST",
                headers:{
                    "Content-Type": "application/json"
                },
                body: JSON.stringify(appointmentData)
            });

            if (response.ok) {
                console.log("Agendamento realizado com sucesso!");
                window.location.href="historico.html"
            } else {
                console.log("Erro ao cadastrar. Verifique os dados.");
            }
            
        } catch (error) {
            console.log("Erro ao agendar. Verifique os dados.");
        }

        
    });
});