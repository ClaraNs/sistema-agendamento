## 📅 Serena Terapia - Sistema de Agendamento  

**Descrição:**  
Sistema de agendamento de consultas para a Serena Terapia, onde os clientes podem selecionar datas disponíveis e agendar atendimentos. O projeto inclui um back-end em **Java + Spring Boot** e um front-end em **HTML, CSS e JavaScript**.


## 🚀 Tecnologias Utilizadas  

### **Back-end**  
- [Java 17+](https://www.oracle.com/java/)  
- [Spring Boot](https://spring.io/projects/spring-boot)  
- [Spring Data JPA](https://spring.io/projects/spring-data-jpa)  
- [Spring Validation](https://spring.io/guides/gs/validating-form-input/)  
- [PostgreSQL](https://www.postgresql.org/)  
- [Flyway](https://flywaydb.org/)  

### **Front-end**  
- [HTML5](https://developer.mozilla.org/pt-BR/docs/Web/HTML)  
- [CSS3](https://developer.mozilla.org/pt-BR/docs/Web/CSS)  
- [JavaScript (ES6+)](https://developer.mozilla.org/pt-BR/docs/Web/JavaScript)  
- [Flatpickr (Calendário)](https://flatpickr.js.org/)  


## 📥 Dependências e Versões Necessárias

### **Pré-requisitos:**  
- Java 17 ou superior  
- PostgreSQL instalado e configurado  
- Maven instalado  


## 📌 Como Usar  

### **Endpoints da API**  

📍 **Cliente (ClientController)**  
| Método | Endpoint               | Descrição |
|--------|------------------------|------------|
| `POST` | `/clients`             | Criar um novo cliente |
| `GET`  | `/clients`             | Listar todos os clientes |
| `GET`  | `/clients/{cpf}`       | Buscar cliente pelo CPF |
| `PATCH` | `/clients/{id}`       | Atualizar dados do cliente |
| `DELETE` | `/clients/{id}`      | Remover cliente |

📍 **Agendamentos (AppointmentController)**  
| Método | Endpoint                          | Descrição |
|--------|-----------------------------------|------------|
| `POST` | `/appointments`                  | Criar um novo agendamento |
| `GET`  | `/appointments`                   | Listar todos os agendamentos |
| `GET`  | `/appointments/date?data=YYYY-MM-DD` | Buscar agendamentos por data |
| `GET`  | `/appointments/{cpf}`            | Buscar agendamentos por CPF |
| `PATCH` | `/appointments/{id}`            | Confirmar agendamento |
| `DELETE` | `/appointments/{id}`           | Cancelar agendamento |

### **Fluxo de Agendamento**  
1. O cliente escolhe uma data no calendário.  
2. O sistema verifica os horários disponíveis.  
3. O cliente confirma o horário.  
4. Caso o CPF não esteja cadastrado, o sistema solicita o cadastro.  
5. A consulta é agendada.  

## ⏭️ Próximos Passos  

- 📅 Melhorar a interface do calendário.  
- 📩 Enviar notificações por e-mail ou WhatsApp sobre confirmação dos agendamentos.
- 📊 Painel administrativo para visualizar consultas.  
