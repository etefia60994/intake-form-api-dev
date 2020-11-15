package com.bfl.intakeform.services;

import com.bfl.intakeform.model.*;
import com.bfl.intakeform.payload.request.AddClientInfo;
import com.bfl.intakeform.payload.response.ApiResponse;
import com.bfl.intakeform.repository.CaseManagerRepository;
import com.bfl.intakeform.repository.ClientRepository;
import com.bfl.intakeform.repository.ClientToServiceProviderRepository;
import com.bfl.intakeform.repository.ServiceProviderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.w3c.dom.ranges.Range;

import javax.persistence.Entity;
import java.util.ArrayList;
import java.util.List;

/**
 * DONE
 *
 * add a client
 * delete a client
 * assign client to manager
 *
 * TODO
 *
 * **/

@Service
public class ClientService {
    @Autowired
    ClientRepository clientRepository;
    @Autowired
    ServiceProviderRepository serviceProviderRepository;
    @Autowired
    CaseManagerService caseManagerService;
    @Autowired
    ClientToServiceProviderRepository clientToServiceProviderRepository;
    @Autowired
    CaseManagerRepository caseManagerRepository;

    /**
     * Add a client
     * @param authentication
     * @param addClientInfo
     *
     * @return  ResponseEntity
     *
     * **/
    public ResponseEntity addClient(Authentication authentication, AddClientInfo addClientInfo){
        CaseManager caseManager = caseManagerService.getCasemanagerFromAuthentication(authentication);
        if(caseManager == null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiResponse(false,"not authorized"));
        }
        //save client first
        Client client = new Client(addClientInfo.getMaritalStatus(),addClientInfo.getSex(),addClientInfo.getRace(),
                addClientInfo.getAge(),caseManager);
        client = clientRepository.save(client);

        Iterable<ServiceProvider> serviceProviders =serviceProviderRepository.findAllById(addClientInfo.getServiceProvidersIds());
        List<ClientToServiceProvider> clientToServiceProviders = new ArrayList<>();
        for(ServiceProvider serviceProvider : serviceProviders){
            ClientToServiceProvider clientToServiceProvider= new ClientToServiceProvider();
            clientToServiceProvider.setClient(client);
            clientToServiceProvider.setServiceProvider(serviceProvider);
        }
        clientToServiceProviderRepository.saveAll(clientToServiceProviders);
        return ResponseEntity.ok(new ApiResponse(true,"added client"));
    }
    /**
     * delete a client
     * a director can delete any client
     * @param authentication
     * @param clientId
     * @return  ResponseEntity
     * **/
    @Transactional
    public ResponseEntity deleteClient(Authentication authentication,Long clientId){
        //check if user is a director
        CaseManager caseManager = caseManagerService.getCasemanagerFromAuthentication(authentication);
        if(caseManager == null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiResponse(false,"not authorized"));
        }
        if(!caseManager.getCaseManagerRole().equals(CaseManagerRoles.DIRECTOR)){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ApiResponse(false,"not a director"));
        }
        Client client;
        try{
            client = clientRepository.findById(clientId).get();
        }catch (Exception e){
            return ResponseEntity.notFound().build();
        }
        if(client == null){
            return ResponseEntity.notFound().build();
        }
        clientRepository.delete(client);
        return ResponseEntity.ok(new ApiResponse(true,"client has been deleted"));
    }
    /**
     * Assign client to a casemanager
     * only supervisors or a director can do this
     * @param clientId
     * @param authentication
     * @param caseManagerId
     * @return ResponseEntity
     * */
    public ResponseEntity assignClient(Authentication authentication,long clientId,long caseManagerId){
        CaseManager caseManager = caseManagerService.getCasemanagerFromAuthentication(authentication);
        if(caseManager == null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiResponse(false,"not authorized"));
        }
        if(caseManager.getCaseManagerRole().equals(CaseManagerRoles.REGULAR)){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ApiResponse(false,"not a director or supervisor"));
        }
        Client client;
        try{
            client = clientRepository.findById(clientId).get();
        }catch (Exception e){
            return ResponseEntity.notFound().build();
        }
        if(client == null){
            return ResponseEntity.notFound().build();
        }
        CaseManager newCaseManager;
        try{
            newCaseManager = caseManagerRepository.findById(caseManagerId).get();
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(false,"no casemanager found with that id"));
        }
        client.setCaseManager(newCaseManager);
        clientRepository.save(client);
        return ResponseEntity.ok(new ApiResponse(true,"client assigned to manager succesfully"));
    }


}
