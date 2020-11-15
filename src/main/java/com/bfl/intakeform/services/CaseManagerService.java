package com.bfl.intakeform.services;

import com.bfl.intakeform.config.AppProperties;
import com.bfl.intakeform.exception.ResourceNotFoundException;
import com.bfl.intakeform.model.CaseManager;
import com.bfl.intakeform.model.CaseManagerRoles;
import com.bfl.intakeform.model.CaseManagerStatus;
import com.bfl.intakeform.payload.request.AddCaseManagerRequest;
import com.bfl.intakeform.payload.request.AuthenticationRequest;
import com.bfl.intakeform.payload.response.ApiResponse;
import com.bfl.intakeform.payload.response.AuthenticationResponse;
import com.bfl.intakeform.repository.CaseManagerRepository;
import com.bfl.intakeform.repository.ClientRepository;
import com.bfl.intakeform.util.TokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;

/***
 * This class is a service class that's going to include most of the casemanager
 * business logics including signing up and signing in
 * DONE
 * Add a caseManager
 * Login for caseManagers
 * Promote caseManager
 * demote supervisor
 *
 * TODO
 * delete casemanager account
 *
 * **/


@Service
public class CaseManagerService {

    @Autowired
    CaseManagerRepository caseManagerRepository;
    @Autowired
    ClientRepository clientRepository;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private TokenProvider tokenProvider;
    @Autowired
    private PasswordEncoder passwordEncoder;


    public CaseManager getCasemanagerFromAuthentication(Authentication authentication){
        CaseManager caseManager;
        try{
            caseManager = caseManagerRepository.findByUserName(authentication.getName()).orElseThrow(
                    () -> new ResourceNotFoundException("Casemanger","userName",authentication.getName())
            );
        }catch (Exception e){
            return null;
        }
        return caseManager;
    }

    /**
     * @param
     * @param addCaseManagerRequest
     * @param authentication
     * @return ResponseEntity
     * **/
    public ResponseEntity AddACaseManager(Authentication authentication, AddCaseManagerRequest addCaseManagerRequest){
        /**
         * checks authorization from authentication
         * */
        CaseManager caseManager1 = getCasemanagerFromAuthentication(authentication);
        if(caseManager1 == null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiResponse(false,"unauthorized user"));
        }
        if(caseManager1.getCaseManagerRole().equals(CaseManagerRoles.REGULAR)){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ApiResponse(false,"not a supervisor or director"));
        }

        /**
         * check if username is unique
         * **/
        if(caseManagerRepository.existsByUserName(addCaseManagerRequest.getUserName())){
            return ResponseEntity.badRequest().body(new ApiResponse(false,"username is taken"));
        }
        /**
         * create the casemanager and save it to database
         * **/
        CaseManager caseManager = createCaseManager(addCaseManagerRequest);
        caseManagerRepository.save(caseManager);
        return ResponseEntity.ok(new ApiResponse(true,"case manager added"));
    }
    /**
     * @param
     * @param addCaseManagerRequest
     * @return CaseManager
     * */
    private CaseManager createCaseManager(AddCaseManagerRequest addCaseManagerRequest){
        CaseManager caseManager = new CaseManager();
        caseManager.setFirstName(addCaseManagerRequest.getFirstName());
        caseManager.setLastName(addCaseManagerRequest.getLastName());
        caseManager.setPassword(passwordEncoder.encode(addCaseManagerRequest.getPassword()));
        caseManager.setUserName(addCaseManagerRequest.getUserName());
        caseManager.setCaseManagerRole(CaseManagerRoles.REGULAR);
        caseManager.setCaseManagerStatus(CaseManagerStatus.NORMAL);
        return caseManager;
    }

    /**
     * @param
     * @param addCaseManagerRequest
     * @return CaseManager
     * **/
    private CaseManager createDirector(AddCaseManagerRequest addCaseManagerRequest){
        CaseManager caseManager = new CaseManager();
        caseManager.setFirstName(addCaseManagerRequest.getFirstName());
        caseManager.setLastName(addCaseManagerRequest.getLastName());
        caseManager.setPassword(passwordEncoder.encode(addCaseManagerRequest.getPassword()));
        caseManager.setUserName(addCaseManagerRequest.getUserName());
        caseManager.setCaseManagerRole(CaseManagerRoles.DIRECTOR);
        return caseManager;
    }

    /**
     * login for caseManagers
     *
     *
     * **/
    public ResponseEntity loginCaseManager(AuthenticationRequest authenticationRequest) throws Exception{
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authenticationRequest.getUsername(),
                        authenticationRequest.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        CaseManager caseManager = caseManagerRepository.findByUserName(authentication.getName()).get();
        if(caseManager.getCaseManagerStatus().equals(CaseManagerStatus.DELETED)){
            return ResponseEntity.badRequest().body(new ApiResponse(false,"Account has been deleted"));
        }

        String jwt = tokenProvider.createToken(authentication);

        return ResponseEntity.ok(new AuthenticationResponse(jwt,"Bearer",caseManager.getCaseManagerRole()));

    }

    /**
     * promote manager to supervisor
     * can only be done by director
     * **/
    public ResponseEntity promoteManager(Authentication authentication,long caseManagerId){
        CaseManager caseManager = getCasemanagerFromAuthentication(authentication);
        if(caseManager == null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiResponse(false,"not authorized"));
        }
        if(!caseManager.getCaseManagerRole().equals(CaseManagerRoles.DIRECTOR)){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ApiResponse(false,"not a director"));
        }
        CaseManager newCaseManager;
        try{
            newCaseManager = caseManagerRepository.findById(caseManagerId).get();
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(false,"no casemanager found with that id"));
        }
        if(newCaseManager.getCaseManagerRole().equals(CaseManagerRoles.REGULAR)) {
            newCaseManager.setCaseManagerRole(CaseManagerRoles.SUPERVISOR);
        }else{
            return ResponseEntity.badRequest().body(new ApiResponse(false,"not a regular casemanager account"));
        }
        caseManagerRepository.save(newCaseManager);
        return ResponseEntity.ok(new ApiResponse(true,"casemanager "+ newCaseManager.getFirstName()+" promoted to supervisor"));
    }
    /**
     * demote supervisor to manager
     * can only be done by director
     * */
    public ResponseEntity demoteSupervisor(Authentication authentication,long caseManagerId){
        CaseManager caseManager = getCasemanagerFromAuthentication(authentication);
        if(caseManager == null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiResponse(false,"not authorized"));
        }
        if(!caseManager.getCaseManagerRole().equals(CaseManagerRoles.DIRECTOR)){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ApiResponse(false,"not a director"));
        }
        CaseManager newCaseManager;
        try{
            newCaseManager = caseManagerRepository.findById(caseManagerId).get();
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(false,"no casemanager found with that id"));
        }
        if(newCaseManager.getCaseManagerRole().equals(CaseManagerRoles.SUPERVISOR)){
            newCaseManager.setCaseManagerRole(CaseManagerRoles.REGULAR);
        }else{
            return ResponseEntity.badRequest().body(new ApiResponse(false,"not a supervisor account"));
        }
        caseManagerRepository.save(newCaseManager);
        return ResponseEntity.ok(new ApiResponse(true,"Supervisor "+newCaseManager.getFirstName()+" demoted to regular caseManager"));
    }

    /**
     * Delete casemanager account by id
     * **/

    /**
     * Restore casemanager account by id
     * **/

}
