package com.bfl.intakeform.controllers;

import com.bfl.intakeform.model.CaseManager;
import com.bfl.intakeform.model.Client;
import com.bfl.intakeform.model.ServiceProvider;
import com.bfl.intakeform.repository.CaseManagerRepository;
import com.bfl.intakeform.repository.ClientRepository;
import com.bfl.intakeform.repository.ServiceProviderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping("/api")
public class ServiceProviderController {

    @Autowired
    private ServiceProviderRepository serviceProviderRepository;

    public ServiceProviderController(ServiceProviderRepository serviceProviderRepository){
        super();
        this.serviceProviderRepository = serviceProviderRepository;
    }

    @GetMapping("/serviceProviders")
    public Iterable<ServiceProvider> serviceProviders(){
        return serviceProviderRepository.findAll();
    }




}
