package com.bfl.intakeform.controllers;

import com.bfl.intakeform.model.CaseManager;
import com.bfl.intakeform.model.Client;
import com.bfl.intakeform.model.ResourceCategory;
import com.bfl.intakeform.repository.CaseManagerRepository;
import com.bfl.intakeform.repository.ClientRepository;
import com.bfl.intakeform.repository.ResourceCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api")
public class ResourceCategoryController {

    @Autowired
    private ResourceCategoryRepository resourceCategoryRepository;

    public ResourceCategoryController(ResourceCategoryRepository resourceCategoryRepository){
        super();
        this.resourceCategoryRepository = resourceCategoryRepository;
    }

    @GetMapping("/resourceCategories")
    public Iterable<ResourceCategory> resourceCategories(HttpServletRequest request)
    {
        System.out.println(request.getSession().getAttribute("caseManagerUser"));
        return resourceCategoryRepository.findAll();
    }




}