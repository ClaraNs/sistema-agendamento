package com.serenaterapias.agendamento.services;

import com.serenaterapias.agendamento.models.Appointment;
import com.serenaterapias.agendamento.models.Client;
import com.serenaterapias.agendamento.repositories.AppointmentRepository;
import com.serenaterapias.agendamento.repositories.ClientRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
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
}
