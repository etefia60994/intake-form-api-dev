package com.bfl.intakeform.services;

import com.bfl.intakeform.model.*;
import com.bfl.intakeform.payload.request.AddServiceProviderRequest;
import com.bfl.intakeform.payload.response.ApiResponse;
import com.bfl.intakeform.repository.ClientRepository;
import com.bfl.intakeform.repository.ClientToServiceProviderRepository;
import com.bfl.intakeform.repository.ServiceProviderRepository;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * DONE
 *
 * add service provider
 * delete service provider
 * add service provider/s to client
 * remove service provider from client
 *
 * TODO
 *
 * **/

@Service
public class ServiceProviderService {
   @Autowired
    ServiceProviderRepository serviceProviderRepository;
   @Autowired
    CaseManagerService caseManagerService;
   @Autowired
    ClientRepository clientRepository;
   @Autowired
    ClientToServiceProviderRepository clientToServiceProviderRepository;

   /**
    * Add service provider
    * @param authentication
    * @param addServiceProviderRequest
    * @return ResponseEntity
    *
    * **/
   public ResponseEntity addServiceProvider(Authentication authentication, AddServiceProviderRequest addServiceProviderRequest){
       CaseManager caseManager = caseManagerService.getCasemanagerFromAuthentication(authentication);
       if(caseManager == null){
           return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiResponse(false,"not authorized"));
       }
       if(!caseManager.getCaseManagerRole().equals(CaseManagerRoles.DIRECTOR)){
           return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ApiResponse(false,"not a director"));
       }

       ServiceProvider serviceProvider = new ServiceProvider(addServiceProviderRequest.getName()
       ,addServiceProviderRequest.getEmail());
       serviceProviderRepository.save(serviceProvider);
       return ResponseEntity.ok(new ApiResponse(true,"service provider added successfully"));
   }
   /**
    * delete service provider
    * -only a director should be able to delete service provider
    * @param authentication
    * @param serviceProviderId
    *
    * **/
   @Transactional
   public ResponseEntity deleteServiceProvider(Authentication authentication,long serviceProviderId){
       //check if user is a director
       CaseManager caseManager = caseManagerService.getCasemanagerFromAuthentication(authentication);
       if(caseManager == null){
           return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiResponse(false,"not authorized"));
       }
       if(!caseManager.getCaseManagerRole().equals(CaseManagerRoles.DIRECTOR)){
           return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ApiResponse(false,"not a director"));
       }
       ServiceProvider serviceProvider;
       //check if service exists
       try{
           serviceProvider = serviceProviderRepository.findById(serviceProviderId).get();
       }catch (Exception e){
           return ResponseEntity.notFound().build();
       }
       if(serviceProvider == null){
           return ResponseEntity.notFound().build();
       }
       //now delete the service provider
       serviceProviderRepository.delete(serviceProvider);
       return ResponseEntity.ok(new ApiResponse(true,"service provider deleted successfully"));
   }
   /**
    * update service provider
    * only director can do this
    *
    * **/
   public ResponseEntity updateServiceProvider(Authentication authentication,AddServiceProviderRequest addServiceProviderRequest,long serviceProviderId){
       CaseManager caseManager = caseManagerService.getCasemanagerFromAuthentication(authentication);
       if(caseManager == null){
           return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiResponse(false,"not authorized"));
       }
       if(!caseManager.getCaseManagerRole().equals(CaseManagerRoles.DIRECTOR)){
           return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ApiResponse(false,"not a director"));
       }
       /**
        * update name and email
        * */
       ServiceProvider serviceProvider;
       //check if service exists
       try{
           serviceProvider = serviceProviderRepository.findById(serviceProviderId).get();
       }catch (Exception e){
           return ResponseEntity.notFound().build();
       }
       if(serviceProvider == null){
           return ResponseEntity.notFound().build();
       }
       serviceProvider.requestSetter(addServiceProviderRequest);
       serviceProviderRepository.save(serviceProvider);
       return ResponseEntity.ok(new ApiResponse(true,"service provider updated successfully"));
   }
   /**
    * add service provider to client
    * check if client belongs to casemanager
    * @param authentication
    * @param serviceProviderId
    * @param clientId
    * @return ResponseEntity
    * **/
   public ResponseEntity addServiceProviderToClient(Authentication authentication,long clientId,long serviceProviderId){
       CaseManager caseManager = caseManagerService.getCasemanagerFromAuthentication(authentication);
       if(caseManager == null){
           return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiResponse(false,"not authorized"));
       }
       Client client;
       try {
            client = clientRepository.findById(clientId).get();
       }catch (Exception e){
           return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(false,"client doesnt exist"));
       }
       if(client == null){
           return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(false,"client doesnt exist"));
       }
       //check if casemanager is a director or client belongs to them
       if(!caseManager.getCaseManagerRole().equals(CaseManagerRoles.DIRECTOR) || client.getCaseManager().getId()!= caseManager.getId()){
           return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ApiResponse(false,"not your client"));
       }
       ServiceProvider serviceProvider;
       try{
           serviceProvider = serviceProviderRepository.findById(serviceProviderId).get();
       }catch (Exception e){
           return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(false,"service provider doesn't exist"));
       }
       if(serviceProvider == null){
           return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(false,"service provider doesn't exist"));
       }
       ClientToServiceProvider clientToServiceProvider = new ClientToServiceProvider();
       clientToServiceProvider.setServiceProvider(serviceProvider);
       clientToServiceProvider.setClient(client);
       clientToServiceProviderRepository.save(clientToServiceProvider);
       return ResponseEntity.ok(new ApiResponse(true,"service provider added to client"));
   }
   /**
    * remove service provider from client
    *
    * @param clientId
    * @param serviceProviderId
    * @param authentication
    * @return ResponseEntity
    * **/
   @Transactional
   public ResponseEntity removeServiceProviderFromClient(Authentication authentication,long clientId,long serviceProviderId){
       CaseManager caseManager = caseManagerService.getCasemanagerFromAuthentication(authentication);
       if(caseManager == null){
           return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiResponse(false,"not authorized"));
       }
       Client client;
       try {
           client = clientRepository.findById(clientId).get();
       }catch (Exception e){
           return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(false,"client doesnt exist"));
       }
       if(client == null){
           return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(false,"client doesnt exist"));
       }
       //check if casemanager is a director or client belongs to them
       if(!caseManager.getCaseManagerRole().equals(CaseManagerRoles.DIRECTOR) || client.getCaseManager().getId()!= caseManager.getId()){
           return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ApiResponse(false,"not your client"));
       }
       ServiceProvider serviceProvider;
       try{
           serviceProvider = serviceProviderRepository.findById(serviceProviderId).get();
       }catch (Exception e){
           return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(false,"service provider doesn't exist"));
       }
       if(serviceProvider == null){
           return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(false,"service provider doesn't exist"));
       }
       clientToServiceProviderRepository.deleteByServiceProviderAndClient(serviceProvider,client);
       return ResponseEntity.ok(new ApiResponse(true,"removed service provider from client"));
   }
}
