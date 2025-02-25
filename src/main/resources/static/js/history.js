document.addEventListener("DOMContentLoaded", async () => {
    const cpf = localStorage.getItem("cpf");
    const listaConsultas = document.getElementById("listaConsultas");
    const filtro = document.getElementById("filtro");

    async function carregarConsultas() {
        try {
            const response = await fetch(`http://localhost:8080/appointments/${cpf}`);

            if (!response.ok) {
                listaConsultas.innerHTML = "<li>Erro ao buscar agendamentos.</li>";
                return;
            }

            const consultas = await response.json();
            exibirConsultas(consultas);
        } catch (error) {
            console.log("Erro ao buscar consultas:", error);
            listaConsultas.innerHTML = "<li>Erro ao carregar os dados.</li>";
        }
    }

    function exibirConsultas(consultas) {
        listaConsultas.innerHTML = "";

        const filtroSelecionado = filtro.value;

        consultas.forEach((consulta) => {
            const dataConsulta = new Date(consulta.data);
            const status = consulta.confirmada ? "Confirmada" : "Pendente";

            if (filtroSelecionado === "todas" ||
                (filtroSelecionado === "pendentes" && status === "Pendente") ||
                (filtroSelecionado === "confirmadas" && status === "Confirmada")
            ) {
                const item = document.createElement("li");

                const textoConsulta = document.createElement("span");
                textoConsulta.innerHTML = `Data: ${dataConsulta.toLocaleDateString("pt-BR")}<br>
                                       Horário: ${dataConsulta.toLocaleTimeString("pt-BR", { hour: "2-digit", minute: "2-digit" })}<br>
                                       Status: ${status}`;

                item.appendChild(textoConsulta);

                if (!consulta.confirmada) {
                    const botaoConfirmar = document.createElement("button");
                    botaoConfirmar.textContent = "Confirmar";
                    botaoConfirmar.classList.add("botao-confirmar");

                    botaoConfirmar.addEventListener("click", () => {
                        localStorage.setItem("consultaId", consulta.id);
                        window.location.href = "confirmacao.html";
                    });

                    item.appendChild(botaoConfirmar);
                }

                listaConsultas.appendChild(item);
            }

        });

        if (listaConsultas.children.length === 0) {
            listaConsultas.innerHTML = "<li>Nenhuma consulta encontrada.</li>";
        }
    }

    filtro.addEventListener("change", () => carregarConsultas());

    carregarConsultas();
});