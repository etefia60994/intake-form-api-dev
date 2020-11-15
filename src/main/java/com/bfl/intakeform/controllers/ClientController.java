package com.bfl.intakeform.controllers;

import com.bfl.intakeform.model.Client;
import com.bfl.intakeform.payload.request.AddClientInfo;
import com.bfl.intakeform.repository.ClientRepository;
import com.bfl.intakeform.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;

/**
 * Endpoints :
 * addCLient
 * removeClient
 * assignClientToCasemanager
 *
 *
 * **/


@RestController
@RequestMapping("/api/clients")
public class ClientController {

    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private ClientService clientService;

    public ClientController(ClientRepository clientRepository){
        super();
        this.clientRepository = clientRepository;
    }

    @PostMapping()
    public ResponseEntity addClient(@RequestBody @Valid AddClientInfo addClientInfo){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return clientService.addClient(auth,addClientInfo);
    }
    @DeleteMapping("{id}")
    public ResponseEntity deleteClient(@PathVariable long id){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return clientService.deleteClient(auth,id);
    }
    @PostMapping("{casemanagerId}/assign/{clientId}")
    public ResponseEntity assignClient(@PathVariable long casemanagerId,
                                       @PathVariable long clientId){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return clientService.assignClient(auth,clientId,casemanagerId);
    }




//    @GetMapping()
//      public Collection<Client> clients(){
//        return clientRepository.findAll();
//    }




}
