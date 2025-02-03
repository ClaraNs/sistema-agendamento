package com.serenaterapias.agendamento.services;

import com.serenaterapias.agendamento.models.Appointment;
import com.serenaterapias.agendamento.models.Client;
import com.serenaterapias.agendamento.repositories.AppointmentRepository;
import com.serenaterapias.agendamento.repositories.ClientRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class AppointmentService {
    private final ClientRepository clientRepository;
    private final AppointmentRepository appointmentRepository;

    @Autowired
    public AppointmentService(ClientRepository clientRepository, AppointmentRepository appointmentRepository) {
        this.clientRepository = clientRepository;
        this.appointmentRepository = appointmentRepository;
    }

    /*public Optional<Client> findClientByCpf(String cpf){
        return clientRepository.findByCpf(cpf);
    }*/

    public Appointment createAppointment(LocalDateTime data, String cpf){
        try{
            //Client client = findClientByCpf(cpf).orElseThrow(() -> new EntityNotFoundException("Client not found"));
            Client client = clientRepository.findByCpf(cpf).orElseThrow(() -> new EntityNotFoundException("Client not found"));
            LocalDateTime endDateTime = data.plusHours(1);

            if(appointmentRepository.existsByDataBetween(data, endDateTime)){
                throw new IllegalArgumentException("Já existe um agendamento para esse horário.");
            }

            Appointment appointment = new Appointment();
            appointment.setPaciente(client);
            appointment.setData(data);

            return appointmentRepository.save(appointment);
        } catch (EmptyResultDataAccessException e){
            throw new EntityNotFoundException("Client not found");
        }
    }

    public List<String> getAvailableTimes(LocalDate date){
        // Define horários de atendimento e intervalo da consulta
        LocalTime startTime = LocalTime.of(9, 0);
        LocalTime endTime = LocalTime.of(18, 0);
        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime endOfDay = date.plusDays(1).atStartOfDay();  // Isso é até 23:59:59
        int interval = 60;

        // Gera Lista com todos os horários possíveis;
        List<String> availableTimes = new ArrayList<>();

        while(startTime.isBefore(endTime)){
            availableTimes.add(startTime.toString());
            startTime = startTime.plusMinutes(interval);
        }

        // Todos os agendamentos para esta data
        List<LocalDateTime> scheduledAppointments = appointmentRepository.findByDataBetween(startOfDay, endOfDay)
                .stream()
                .map(Appointment::getData)
                .toList();

        System.out.println("Agendamentos existentes: " + scheduledAppointments);

        // Converter LocalDateTime para String (apenas a parte do horário)
        List<String> scheduledTimes = scheduledAppointments.stream().map(dt->dt.toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm")))
                .toList();

        availableTimes.removeAll(scheduledTimes);
        /*for(String time: scheduledTimes){
            // Adiciono apenas os horarios livres ( que nao aparecem na lista de marcados )
            if(!scheduledTimes.contains(time))
                availableTimes.add(time);
        }*/

        return availableTimes;
    }
}
