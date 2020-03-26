package com.bfl.intakeform.controllers;

import com.bfl.intakeform.model.Client;
import com.bfl.intakeform.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping("/api")
public class ClientController {

    @Autowired
    private ClientRepository clientRepository;

    public ClientController(ClientRepository clientRepository){
        super();
        this.clientRepository = clientRepository;
    }

    @GetMapping("/clients")
      public Collection<Client> clients(){
        return clientRepository.findAll();
    }




}
