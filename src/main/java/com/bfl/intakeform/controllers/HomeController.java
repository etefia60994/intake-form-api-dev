package com.bfl.intakeform.controllers;

import com.bfl.intakeform.DTO.LoginDTO;
import com.bfl.intakeform.DTO.RegisterDTO;
import com.bfl.intakeform.DTO.Returnresponse;
import com.bfl.intakeform.model.CaseManager;
import com.bfl.intakeform.repository.CaseManagerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import javax.persistence.criteria.CriteriaBuilder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Optional;
@RestController
@RequestMapping("/api")
public class HomeController {
    @Autowired
    CaseManagerRepository caseManagerRepository;
    private static final String userSessionKey = "caseManagerUser";

    public CaseManager getUserFromSession(HttpSession session) {
        Integer userId = (Integer) session.getAttribute(userSessionKey);
        if (userId == null) {
            return null;
        }

        Optional<CaseManager> caseManager = caseManagerRepository.findById(userId);

        return caseManager.get();
    }

    private static void setUserInSession(HttpSession session, CaseManager caseManager) {
        session.setAttribute(userSessionKey, caseManager.getId());
    }
    @PostMapping("/registerManager")
    Returnresponse caseworkerRegister(@Valid @RequestBody RegisterDTO registerform, HttpServletRequest request){

     CaseManager existing =caseManagerRepository.findByUsername(registerform.getUsername());
        if(existing!= null){
            return new Returnresponse("username already exists");
        }
        CaseManager caseManager = new CaseManager(registerform.getUsername(),registerform.getPassword(),registerform.getFirstname(),registerform.getLastname());
        caseManagerRepository.save(caseManager);
        return new Returnresponse("succesfully registered");

    }
    @PostMapping("/login")
    Returnresponse caseworkerLogin(@Valid @RequestBody LoginDTO loginform, HttpServletRequest request){
        System.out.println(request.getSession().getAttribute(userSessionKey));
        CaseManager caseManager=caseManagerRepository.findByUsername(loginform.getUsername());
        Returnresponse returnresponse = new Returnresponse("error");
        try{

       if(caseManager == null){
           //user doesn't exist
           returnresponse.setResponse("user doesn't exist");
           return returnresponse;
       }else{
      String password = loginform.getPassword();
        if(!caseManager.isMatchingPassword(password)){
            //password is not correct
            returnresponse.setResponse("password is not correct");
            return returnresponse;
        }
       }
        }catch (Exception e){
            //something went wrong
            returnresponse.setResponse("something went wrong");
            return returnresponse;
        }
     setUserInSession(request.getSession(),caseManager);
        System.out.println(request.getSession().getAttribute(userSessionKey));
  returnresponse.setResponse("login succesful");
  return returnresponse;
    }
}
