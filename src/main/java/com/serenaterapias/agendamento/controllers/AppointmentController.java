package com.serenaterapias.agendamento.controllers;

import com.serenaterapias.agendamento.models.Appointment;
import com.serenaterapias.agendamento.models.Client;
import com.serenaterapias.agendamento.repositories.AppointmentRepository;
import com.serenaterapias.agendamento.repositories.ClientRepository;
import com.serenaterapias.agendamento.services.AppointmentService;
import com.serenaterapias.agendamento.validation.RequestAppointment;
import com.serenaterapias.agendamento.validation.RequestAppointmentUpdate;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/appointments")
public class AppointmentController {

    private final AppointmentService appointmentService;

    @Autowired
    private AppointmentRepository appointmentRepository;
    @Autowired
    private ClientRepository clientRepository;

    private AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    public static AppointmentController createAppointmentController(AppointmentService appointmentService) {
        return new AppointmentController(appointmentService);
    }

    @GetMapping
    public ResponseEntity<List<Appointment>> getAllAppointment(){
        List<Appointment> appointments = appointmentRepository.findAll();

        if(appointments.isEmpty()){
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(appointments);
    }

    // Receber apenas o cpf e pegar o cliente a partir disso
    @PostMapping
    public ResponseEntity<Appointment> createAppointment(@RequestBody @Valid RequestAppointment data){
        Appointment appointment = appointmentService.createAppointment(data.data(), data.paciente_cpf());
        return ResponseEntity.status(201).body(appointment);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAppointment(@PathVariable Long id){

        if(appointmentRepository.existsById(id)) {
            appointmentRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}/date")
    public ResponseEntity<Appointment> updateAppointmentDate(@PathVariable Long id, @RequestBody @Valid RequestAppointmentUpdate data){
        if(data.data() == null)
            return ResponseEntity.badRequest().build();

        Appointment appointment = appointmentRepository.findById(id).orElse(null);

        if(appointment == null) {
            return ResponseEntity.notFound().build();
        }

        appointment.setData(data.data());
        appointmentRepository.save(appointment);
        return ResponseEntity.ok(appointment);
    }

    // Confirmar agendamento
    @PatchMapping("/{id}")
    public ResponseEntity<Appointment> updateAppointmentStatus(@PathVariable Long id, @RequestBody boolean confirm) {
        /*//Isso seria misturar logicas
        if (!confirm) {
            appointmentRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }*/

        Appointment appointment = appointmentRepository.findById(id).orElse(null);

        if (appointment == null)
            return ResponseEntity.notFound().build();

        appointment.setConfirmada(confirm);
        return ResponseEntity.ok(appointment);
    }

    // Buscar agendamentos por cliente
    @GetMapping("/{cpf}")
    public ResponseEntity<List<Appointment>> getAppointmentByCpf(@PathVariable String cpf){
        Client client = clientRepository.findByCpf(cpf).orElse(null);

        if(client == null)
            return ResponseEntity.notFound().build();

        List<Appointment> appointments = appointmentRepository.findByPaciente(client);

        if(appointments.isEmpty()){
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(appointments);
    }

    // Buscar agendamentos por data
    @GetMapping("/date")
    public ResponseEntity<List<Appointment>> getAppointmentByDate(@RequestParam LocalDate data){
        try {
            List<Appointment> appointments = appointmentRepository.findByDataBetween(data.atStartOfDay(),data.plusDays(1).atStartOfDay());

            if (appointments.isEmpty()) {
                System.out.println("Veio vazio");
                return ResponseEntity.noContent().build(); // Retorna 204 se não houver agendamentos
            }

            return ResponseEntity.ok(appointments);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // Buscar agendamentos a serem confirmados - Possível ligar a disparo de mensagem
    @GetMapping("/pending")
    public ResponseEntity<List<Appointment>> getPendingAppointments(){
        List<Appointment> pendingAppointments = appointmentRepository.findByConfirmadaFalse();

        if(pendingAppointments.isEmpty()){
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(pendingAppointments);
    }

    @GetMapping("/available-times")
    public ResponseEntity<List<String>> getAvailableTimes(@RequestParam String data){
        try{
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate localDate = LocalDate.parse(data, formatter);

            List<String> availableTimes = appointmentService.getAvailableTimes(localDate);

            if(availableTimes.isEmpty()){
                return ResponseEntity.noContent().build();
            }

            return ResponseEntity.ok(availableTimes);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.badRequest().body(null);
        }
    }
}
