document.addEventListener("DOMContentLoaded", () =>{
    flatpickr("#datepicker",{
        inline: true,  // Exibe o calendário diretamente no DOM
        enableTime: false,
        dateFormat:"d/m/Y",
        minDate: "today",
        locale:"pt",
        onChange: (selectedDates, dateStr) => {
            console.log("Data escolhida:", dateStr); // Exemplo: 31/01/2025
            fetchHorariosDisponiveis(dateStr);
        }
    });

    const modal = document.getElementById("horariosModal");
    const horariosLista = document.getElementById("horariosLista");
    const agendarModal = document.getElementById("agendarModal");
    const fecharModal = document.getElementById("fecharModal");

    fecharModal.addEventListener("click", () => {
        modal.classList.add("hidden");
    });

    function formatarDataISO(data) {
        const partes = data.split("/"); // Supondo que a entrada seja "dd/MM/yyyy"
        if (partes.length !== 3) return data; // Caso já esteja correto, retorna como está
        const [dia, mes, ano] = partes;
        return `${ano}-${mes}-${dia}`; // Retorna "yyyy-MM-dd"
    }

    // Função para buscar horários disponíveis do backend
    async function fetchHorariosDisponiveis(data) {
        try {
            const dataFormatada = formatarDataISO(data); // Converter data para yyyy-MM-dd
            const response = await fetch(`http://localhost:8080/appointments/available-times?data=${encodeURIComponent(dataFormatada)}`);

            if (!response.ok) throw new Error("Erro ao buscar horários");

            const horarios = await response.json();

            horariosLista.innerHTML = ""; // Limpa lista de horários

            if (horarios.length === 0) {
                horariosLista.innerHTML = "<li>Sem horários disponíveis</li>";
            } else {
                horarios.forEach(horario => {
                    const li = document.createElement("li"); // ✅ Correção: Criar corretamente um elemento <li>
                    li.textContent = horario;
                    li.addEventListener("click", () => {
                        alert(`Horário ${horario} selecionado!`);
                    });

                    horariosLista.appendChild(li);
                });
            }

            modal.classList.remove("hidden");
        } catch (error) {
            console.error(error);
            alert("Erro ao buscar horários, tente novamente");
        }
    }
});
