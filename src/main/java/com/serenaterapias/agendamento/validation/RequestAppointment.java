package com.serenaterapias.agendamento.validation;

import com.serenaterapias.agendamento.models.Client;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;

import java.time.LocalDateTime;
import java.util.Date;

public record RequestAppointment(@FutureOrPresent LocalDateTime data,
                                 @NotBlank String paciente_cpf) {

}
