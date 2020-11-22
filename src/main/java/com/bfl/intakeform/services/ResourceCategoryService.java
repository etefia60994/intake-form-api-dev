package com.bfl.intakeform.services;

import com.bfl.intakeform.model.*;
import com.bfl.intakeform.payload.request.AddResourceCategoryRequest;
import com.bfl.intakeform.payload.response.ApiResponse;
import com.bfl.intakeform.repository.ClientRepository;
import com.bfl.intakeform.repository.ClientToResourceCategoryRepository;
import com.bfl.intakeform.repository.ResourceCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;

/**
 * TODO
 * add resource category to client
 *  -- need to create query to find all service providers containing that resource category
 *  -- then add both the resource category and service providers
 * remove resource category from client
 *  -- need to query and find all service provider containing that resource category
 *  -- remove both the resource category and all the service providers associated with resource
 *     category from client
 *
 * **/
public class ResourceCategoryService {
    @Autowired
    ResourceCategoryRepository resourceCategoryRepository;
    @Autowired
    CaseManagerService caseManagerService;
    @Autowired
    ClientRepository clientRepository;

    @Autowired
    ClientToResourceCategoryRepository clientToResourceCategoryRepository;

    /**
     * Add a resource category
     * **/
    public ResponseEntity addResourceCategory(Authentication authentication, AddResourceCategoryRequest addResourceCategoryRequest){
        CaseManager caseManager = caseManagerService.getCasemanagerFromAuthentication(authentication);
        if(caseManager == null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiResponse(false,"not authorized"));
        }
        if(!caseManager.getCaseManagerRole().equals(CaseManagerRoles.DIRECTOR)){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ApiResponse(false,"not a director"));
        }
        ResourceCategory resourceCategory = new ResourceCategory();
        resourceCategory.requestSetter(addResourceCategoryRequest);
        resourceCategoryRepository.save(resourceCategory);
        return ResponseEntity.ok(new ApiResponse(true,"resource category created succesfully"));
    }
    /**
     * update a resource category
     * **/
    public ResponseEntity updateResourceCategory(Authentication authentication,
                                                 AddResourceCategoryRequest addResourceCategoryRequest,
                                                 long resourceCategoryId){
        CaseManager caseManager = caseManagerService.getCasemanagerFromAuthentication(authentication);
        if(caseManager == null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiResponse(false,"not authorized"));
        }
        if(!caseManager.getCaseManagerRole().equals(CaseManagerRoles.DIRECTOR)){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ApiResponse(false,"not a director"));
        }
        ResourceCategory resourceCategory;
        try{
            resourceCategory = resourceCategoryRepository.findById(resourceCategoryId).get();
        }catch (Exception e){
            return ResponseEntity.notFound().build();
        }
        if(resourceCategory == null){
            return ResponseEntity.notFound().build();
        }
        resourceCategory.requestSetter(addResourceCategoryRequest);
        resourceCategoryRepository.save(resourceCategory);
        return ResponseEntity.ok(new ApiResponse(true,"resource category updated succesfully" ));
    }

    /**
     * Delete resource category
     * Transactional
     * **/
    @Transactional
    public ResponseEntity deleteResouceCategory(Authentication authentication,long resourceCategoryId){
        CaseManager caseManager = caseManagerService.getCasemanagerFromAuthentication(authentication);
        if(caseManager == null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiResponse(false,"not authorized"));
        }
        if(!caseManager.getCaseManagerRole().equals(CaseManagerRoles.DIRECTOR)){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ApiResponse(false,"not a director"));
        }
        ResourceCategory resourceCategory;
        try{
            resourceCategory = resourceCategoryRepository.findById(resourceCategoryId).get();
        }catch (Exception e){
            return ResponseEntity.notFound().build();
        }
        if(resourceCategory == null){
            return ResponseEntity.notFound().build();
        }
        resourceCategoryRepository.delete(resourceCategory);
        return ResponseEntity.ok(new ApiResponse(true,"resource category deleted successfully"));
    }
    /**
     * Add resource category to client
     * **/
    public ResponseEntity addResourceCategoryToClient(Authentication authentication,long clientId,long resourceCategoryId){
        CaseManager caseManager = caseManagerService.getCasemanagerFromAuthentication(authentication);
        if(caseManager == null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiResponse(false,"not authorized"));
        }
        Client client;
        try{
            client = clientRepository.findById(clientId).get();
        }catch (Exception e){
            return ResponseEntity.notFound().build();
        }
        ResourceCategory resourceCategory;
        try{
            resourceCategory = resourceCategoryRepository.findById(resourceCategoryId).get();
        }catch (Exception e){
            return ResponseEntity.notFound().build();
        }
        ClientToResourceCategory clientToResourceCategory = new ClientToResourceCategory();
        clientToResourceCategory.setClient(client);
        clientToResourceCategory.setResourceCategory(resourceCategory);
        try{
            clientToResourceCategoryRepository.save(clientToResourceCategory);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(new ApiResponse(false,"problem adding resource category"));
        }
        return ResponseEntity.ok(new ApiResponse(true,"successfully added resource to client"));
    }

    /**
     * remove resource category from client
     * */
    @Transactional
    public ResponseEntity removeResourceCategoryFromClient(Authentication authentication,long clientId, long resourceCategoryId){
        Client client;
        try{
            client = clientRepository.findById(clientId).get();
        }catch (Exception e){
            return ResponseEntity.notFound().build();
        }
        ResourceCategory resourceCategory;
        try{
            resourceCategory = resourceCategoryRepository.findById(resourceCategoryId).get();
        }catch (Exception e){
            return ResponseEntity.notFound().build();
        }
        boolean exists = clientToResourceCategoryRepository.existsByClientAndResourceCategory(client,resourceCategory);
        if(!exists){
            return ResponseEntity.badRequest().body(new ApiResponse(false,"client doesnt have resource category"));
        }
        clientToResourceCategoryRepository.deleteByClientAndResourceCategory(client,resourceCategory);
        return ResponseEntity.ok(new ApiResponse(true,"removed resource from client"));
    }


}
