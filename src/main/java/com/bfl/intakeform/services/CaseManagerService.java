package com.bfl.intakeform.services;

import com.bfl.intakeform.model.CaseManager;
import com.bfl.intakeform.repository.CaseManagerRepository;
import com.bfl.intakeform.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


@Service
public class CaseManagerService {

    @Autowired
    CaseManagerRepository caseManagerRepository;
    @Autowired
    ClientRepository clientRepository;

}
