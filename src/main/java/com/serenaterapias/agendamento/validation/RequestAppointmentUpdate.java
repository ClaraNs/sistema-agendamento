package com.serenaterapias.agendamento.validation;

import jakarta.validation.constraints.FutureOrPresent;

import java.time.LocalDateTime;
import java.util.Date;

public record RequestAppointmentUpdate(@FutureOrPresent LocalDateTime data) {
}
