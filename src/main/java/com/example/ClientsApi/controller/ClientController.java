package com.example.ClientsApi.controller;

import com.example.ClientsApi.exception.ResourceNotFoundException;
import com.example.ClientsApi.model.Client;
import com.example.ClientsApi.model.PhoneNumber;
import com.example.ClientsApi.repository.ClientRepository;
import com.example.ClientsApi.repository.PhoneNumberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ClientController {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private PhoneNumberRepository phoneNumberRepository;

    @PostMapping("/clients")
    public Client createClient(@Valid @RequestBody Client client){
        return clientRepository.save(client);
    }

    @GetMapping("/clients")
    public List<Client> displayClients(){
        return clientRepository.findAll();
    }
    @PutMapping("/clients/{clientId}/refresh")
    public Client updateClient(@PathVariable(value = "clientId") Long clientId,
                               @Valid @RequestBody Client client){
        Client client1=clientRepository.findById(clientId)
                .orElseThrow(()->new ResourceNotFoundException("Client with id= "+clientId+ " not found"));
        client1.setName(client.getName());
        client1.setLastName(client.getLastName());
        client1.setSecondName(client.getSecondName());
        Client updatedClient=clientRepository.save(client1);
        return updatedClient;
    }
    @DeleteMapping("/clients/{clientId}/remove")
    public String deleteClient(@PathVariable (value = "clientId" )Long clientId){
        return clientRepository.findById(clientId)
                .map(client -> {
                    clientRepository.delete(client);
                    return "Client with id= "+clientId+ " and his numbers are deleted successful";
                }).orElseThrow(()->new ResourceNotFoundException("client not found with id = "+clientId));
    }
}
