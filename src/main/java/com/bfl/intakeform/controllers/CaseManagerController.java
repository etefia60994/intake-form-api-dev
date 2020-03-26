package com.bfl.intakeform.controllers;

import com.bfl.intakeform.model.CaseManager;
import com.bfl.intakeform.repository.CaseManagerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class CaseManagerController {

    @Autowired
    private CaseManagerRepository caseManagerRepository;

    public CaseManagerController(CaseManagerRepository caseManagerRepository){
        super();
        this.caseManagerRepository = caseManagerRepository;
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



    @PostMapping("/caseManager")
    ResponseEntity<CaseManager> createCaseManager(@Valid @RequestBody CaseManager caseManager) throws URISyntaxException {
        CaseManager result= caseManagerRepository.save(caseManager);
        return ResponseEntity.created(new URI("/api/category" + result.getId())).body(result);

    }


    @PutMapping("/caseManager/{id}")
    ResponseEntity<CaseManager> updateCaseManager(@Valid @RequestBody CaseManager caseManager){
        CaseManager result= caseManagerRepository.save(caseManager);
        return ResponseEntity.ok().body(result);
    }



    @DeleteMapping("/caseManager/{id}")
    ResponseEntity<?> deleteCaseManager(@PathVariable Long id){
        caseManagerRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }



}
