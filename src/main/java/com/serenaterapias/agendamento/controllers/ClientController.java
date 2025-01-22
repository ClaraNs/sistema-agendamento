package com.serenaterapias.agendamento.controllers;

import com.serenaterapias.agendamento.models.Client;
import com.serenaterapias.agendamento.repositories.ClientRepository;
import com.serenaterapias.agendamento.validation.RequestClient;
import com.serenaterapias.agendamento.validation.RequestClientUpdate;
import exception.ClientNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clients")
public class ClientController {
    @Autowired
    private ClientRepository clientRepository;

    @GetMapping
    public ResponseEntity<List<Client>> getAllClients() {
        List<Client> clients = clientRepository.findAll();

        if(clients.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(clients);
    }

    @GetMapping("/{cpf}")
    public ResponseEntity<Client> getClientByCpf(@PathVariable String cpf) {
        Client client = clientRepository.findByCpf(cpf)
                .orElseThrow(() -> new ClientNotFoundException("Cliente com CPF " + cpf + " não encontrado"));

        if(client == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(client);
    }

    @PostMapping
    public ResponseEntity<Client> createClient(@RequestBody @Valid RequestClient data){
        Client client = new Client();
        client.setNome(data.nome());
        client.setCpf(data.cpf());
        client.setTelefone(data.telefone());
        client.setEmail(data.email());
        client.setData_nascimento(data.data_nascimento());
        clientRepository.save(client);
        return ResponseEntity.status(201).body(client);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClient(@PathVariable Long id){
        if(clientRepository.existsById(id)){
            clientRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else{
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Client> updateClient(@PathVariable Long id, @RequestBody @Valid RequestClientUpdate data){
        if(clientRepository.existsById(id)){
            Client client = clientRepository.findById(id).get();
            if(data.nome() != null)
                client.setNome(data.nome());
            if(data.telefone() != null)
                client.setTelefone(data.telefone());
            if(data.email() != null)
                client.setEmail(data.email());

            clientRepository.save(client);
            return ResponseEntity.ok(client);
        }

        return ResponseEntity.notFound().build();
    }

}
