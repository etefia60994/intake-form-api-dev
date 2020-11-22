package com.bfl.intakeform.controllers;

import com.bfl.intakeform.model.CaseManager;
import com.bfl.intakeform.model.Client;
import com.bfl.intakeform.model.ServiceProvider;
import com.bfl.intakeform.payload.request.AddServiceProviderRequest;
import com.bfl.intakeform.repository.CaseManagerRepository;
import com.bfl.intakeform.repository.ClientRepository;
import com.bfl.intakeform.repository.ServiceProviderRepository;
import com.bfl.intakeform.services.ServiceProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;

/**
 * Endpoints :
 * addServiceProvider
 * deleteServiceProvider
 * addServiceProviderToClient
 * removeServiceProviderFromClient
 *
 * **/
@RestController
@RequestMapping("/api/serviceProviders")
public class ServiceProviderController {

    @Autowired
    private ServiceProviderRepository serviceProviderRepository;
    @Autowired
    private ServiceProviderService serviceProviderService;

    public ServiceProviderController(ServiceProviderRepository serviceProviderRepository){
        super();
        this.serviceProviderRepository = serviceProviderRepository;
    }

    @PostMapping()
    public ResponseEntity addServiceProvider(@RequestBody @Valid AddServiceProviderRequest addServiceProviderRequest){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return serviceProviderService.addServiceProvider(auth,addServiceProviderRequest);
    }
    @PutMapping("{id}")
    public ResponseEntity updateServiceProvider(@RequestBody @Valid AddServiceProviderRequest addServiceProviderRequest,
                                                @PathVariable long id){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return serviceProviderService.updateServiceProvider(auth,addServiceProviderRequest,id);
    }
    @DeleteMapping("{id}")
    public ResponseEntity deleteServiceProvider(@PathVariable long id){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return serviceProviderService.deleteServiceProvider(auth,id);
    }
    @PostMapping("{serviceproviderId}/add/{clientId}")
    public ResponseEntity addServiceProviderToClient(@PathVariable long serviceproviderId,
                                                     @PathVariable long clientId){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return serviceProviderService.addServiceProviderToClient(auth,clientId,serviceproviderId);
    }
    @PostMapping("{serviceproviderId}/remove/{clientId}")
    public ResponseEntity removeServiceProviderFromClient(@PathVariable long serviceproviderId,
                                                          @PathVariable long clientId){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return serviceProviderService.removeServiceProviderFromClient(auth,clientId,serviceproviderId);
    }


    @GetMapping("")
    public Iterable<ServiceProvider> serviceProviders(){
        return serviceProviderRepository.findAll();
    }




}
