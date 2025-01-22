package com.serenaterapias.agendamento.validation;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public record RequestClient(@NotBlank String nome,
                            @NotBlank String cpf,
                            @NotBlank String telefone,
                            @NotBlank String email,
                            @PastOrPresent Date data_nascimento){
}
