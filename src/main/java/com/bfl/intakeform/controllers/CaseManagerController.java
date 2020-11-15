package com.bfl.intakeform.controllers;

import com.bfl.intakeform.model.CaseManager;
import com.bfl.intakeform.payload.request.AddCaseManagerRequest;
import com.bfl.intakeform.payload.request.AuthenticationRequest;
import com.bfl.intakeform.repository.CaseManagerRepository;
import com.bfl.intakeform.services.CaseManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

/**
 * 
 * Endpoints :
 *
 * addCasemanager
 * deleteCasemanager
 * promoteCasemanger
 * demoteSupervisorCasemanager
 *
 * **/

@RestController
@RequestMapping("/api/casemanager")
public class CaseManagerController {

    @Autowired
    private CaseManagerRepository caseManagerRepository;

    @Autowired
    private CaseManagerService caseManagerService;
    public CaseManagerController(CaseManagerRepository caseManagerRepository){
        super();
        this.caseManagerRepository = caseManagerRepository;
    }


    @PostMapping("add")
    public ResponseEntity addCaseManager(@RequestBody @Valid AddCaseManagerRequest addCaseManagerRequest){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return caseManagerService.AddACaseManager(auth,addCaseManagerRequest);
    }
    @PostMapping("login")
    public ResponseEntity loginCaseManager(@RequestBody @Valid AuthenticationRequest authenticationRequest) throws Exception{
        return caseManagerService.loginCaseManager(authenticationRequest);
    }
    @PostMapping("promote/{id}")
    public ResponseEntity promoteCaseManager(@PathVariable long id){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return caseManagerService.promoteManager(auth,id);
    }

    @PostMapping("demote/{id}")
    public ResponseEntity demoteCaseManager(@PathVariable long id){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return caseManagerService.demoteSupervisor(auth,id);
    }

    @GetMapping("/caseManagers")
    public Iterable<CaseManager> caseManagers(){
        return caseManagerRepository.findAll();
    }

    //category/2
    @GetMapping("/caseManagers/{id}")
    ResponseEntity<?> getCategory(@PathVariable Long id){
        Optional<CaseManager> caseManager = caseManagerRepository.findById(id);
        return caseManager.map(response -> ResponseEntity.ok().body(response))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));

    }



//    @PostMapping("/caseManager")
//    ResponseEntity<CaseManager> createCaseManager(@Valid @RequestBody CaseManager caseManager) throws URISyntaxException {
//        CaseManager result= caseManagerRepository.save(caseManager);
//        return ResponseEntity.created(new URI("/api/category" + result.getId())).body(result);
//
//    }
//
//
//    @PutMapping("/caseManager/{id}")
//    ResponseEntity<CaseManager> updateCaseManager(@Valid @RequestBody CaseManager caseManager){
//        CaseManager result= caseManagerRepository.save(caseManager);
//        return ResponseEntity.ok().body(result);
//    }
//
//
//
//    @DeleteMapping("/caseManager/{id}")
//    ResponseEntity<?> deleteCaseManager(@PathVariable Long id){
//        caseManagerRepository.deleteById(id);
//        return ResponseEntity.ok().build();
//    }
//


}
