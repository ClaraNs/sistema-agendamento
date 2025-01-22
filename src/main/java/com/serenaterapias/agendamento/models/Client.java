package com.serenaterapias.agendamento.models;

import com.serenaterapias.agendamento.validation.ValidadorCPF;
import com.serenaterapias.agendamento.validation.ValidadorEmail;
import jakarta.persistence.*;
import jakarta.validation.constraints.PastOrPresent;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.UUID;

@Entity
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String nome;

    @Column(unique = true)
    private String cpf;
    private String telefone;

    @Column(unique = true)
    private String email;
    @PastOrPresent
    private Date data_nascimento;

    public Client() {
    }

    public Client(Long id, String nome, String cpf, String telefone, String email, Date data_nascimento) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.telefone = telefone;
        this.email = email;
        this.data_nascimento = data_nascimento;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        // todos os caracteres que nao sao digitos sao retirados
        cpf = cpf.replaceAll("[^\\d]", "");

        if (!ValidadorCPF.isValid(cpf)) {
            throw new IllegalArgumentException("CPF inválido");
        }

        this.cpf = cpf;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if(!ValidadorEmail.isValid(email))
            throw new IllegalArgumentException("Email inválido");
        this.email = email;
    }

    public Date getData_nascimento() {
        return data_nascimento;
    }

    public void setData_nascimento(Date data_nascimento) {
        this.data_nascimento = data_nascimento;
    }
}
