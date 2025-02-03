package com.serenaterapias.agendamento.repositories;

import com.serenaterapias.agendamento.models.Appointment;
import com.serenaterapias.agendamento.models.Client;
import jakarta.validation.constraints.FutureOrPresent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    //List<Appointment> findByDataBetween(@FutureOrPresent LocalDateTime dataAfter, @FutureOrPresent LocalDateTime dataBefore);
    List<Appointment> findByPaciente(Client paciente);
    List<Appointment> findByConfirmadaFalse();
    List<Appointment> findByData(@FutureOrPresent LocalDateTime data);
   // boolean existsByData(@FutureOrPresent LocalDateTime data);
    boolean existsByDataBetween(@FutureOrPresent LocalDateTime startDateTime, LocalDateTime endDateTime);
    @Query("SELECT a FROM Appointment a WHERE a.data >= :startOfDay AND a.data < :endOfDay")
    List<Appointment> findByDataBetween(@Param("startOfDay") LocalDateTime startOfDay, @Param("endOfDay") LocalDateTime endOfDay);
}
