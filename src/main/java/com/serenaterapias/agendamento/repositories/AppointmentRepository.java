package com.serenaterapias.agendamento.repositories;

import com.serenaterapias.agendamento.models.Appointment;
import com.serenaterapias.agendamento.models.Client;
import jakarta.validation.constraints.FutureOrPresent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    List<Appointment> findByPaciente(Client paciente);
    List<Appointment> findByConfirmadaFalse();
    List<Appointment> findByData(@FutureOrPresent LocalDateTime data);
   // boolean existsByData(@FutureOrPresent LocalDateTime data);
    boolean existsByDataBetween(@FutureOrPresent LocalDateTime startDateTime, LocalDateTime endDateTime);
}
