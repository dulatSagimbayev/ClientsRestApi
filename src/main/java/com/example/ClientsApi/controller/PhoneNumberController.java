package com.example.ClientsApi.controller;

import com.example.ClientsApi.exception.ResourceNotFoundException;
import com.example.ClientsApi.model.Client;
import com.example.ClientsApi.model.PhoneNumber;
import com.example.ClientsApi.repository.ClientRepository;
import com.example.ClientsApi.repository.PhoneNumberRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@RestController
@RequestMapping("/api")
public class PhoneNumberController {
    @Autowired
    private PhoneNumberRepository phoneNumberRepository;

    @Autowired
    private ClientRepository clientRepository;

    @GetMapping("/clients/{clientId}/numbers")
    public List<PhoneNumber> getContactByClientId(@PathVariable Long clientId) throws NotFoundException {

        if(!clientRepository.existsById(clientId)) {
            throw new NotFoundException("Student not found!");
        }

        return phoneNumberRepository.findByClientId(clientId);
    }

    @PostMapping("/clients/{clientId}/numbers")
    public PhoneNumber createPhoneNumber(@PathVariable (value = "clientId") Long clientId,
                                    @Valid @RequestBody PhoneNumber phoneNumber){
        return clientRepository.findById(clientId)
                .map(client -> {
            phoneNumber.setClient(client);
            return phoneNumberRepository.save(phoneNumber);
        }).orElseThrow(()->new ResourceNotFoundException("clientId "+clientId+" not found"));
    }

    @PutMapping("/clients/{clientId}/refresh/{numberId}")
    public PhoneNumber updatePhoneNumber(@PathVariable (value = "clientId") Long clientId,
                                    @PathVariable (value = "numberId") Long numberId,
                                    @Valid @RequestBody PhoneNumber phoneNumber){

        Client client1=clientRepository.findById(clientId)
                .orElseThrow(()->new ResourceNotFoundException("Client with id= "+clientId+ " not found"));
        PhoneNumber phoneNumber1 = phoneNumberRepository.findById(numberId)
                .orElseThrow(()->new ResourceNotFoundException("Number with id= "+numberId+ " not found"));
        phoneNumber1.setPhoneNumber(phoneNumber.getPhoneNumber());
        phoneNumber1.setType(phoneNumber.getType());
        return phoneNumberRepository.save(phoneNumber1);
    }
    @DeleteMapping("/clients/{clientId}/numbers/{numberId}/remove")
    public String deletePhoneNumber(@PathVariable (value = "clientId") Long clientId,
                                    @PathVariable (value = "numberId") Long numberId){
        clientRepository.findById(clientId).orElseThrow(()->new ResourceNotFoundException("client with id= "+clientId+" not found"));
        return phoneNumberRepository.findById(numberId).map(phoneNumber -> {
            phoneNumberRepository.delete(phoneNumber);
            return "Phone number with id= "+numberId+" is deleted successful";
        }).orElseThrow(()->new ResourceNotFoundException("Phone number with id= "+numberId+" not found"));
    }

}
