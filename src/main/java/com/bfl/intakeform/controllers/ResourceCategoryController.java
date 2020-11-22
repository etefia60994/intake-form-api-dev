package com.bfl.intakeform.controllers;

import com.bfl.intakeform.model.CaseManager;
import com.bfl.intakeform.model.Client;
import com.bfl.intakeform.model.ResourceCategory;
import com.bfl.intakeform.payload.request.AddResourceCategoryRequest;
import com.bfl.intakeform.repository.CaseManagerRepository;
import com.bfl.intakeform.repository.ClientRepository;
import com.bfl.intakeform.repository.ResourceCategoryRepository;
import com.bfl.intakeform.services.ResourceCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceEditor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;

@RestController
@RequestMapping("/api/resourceCategory")
public class ResourceCategoryController {




    @Autowired
    private ResourceCategoryRepository resourceCategoryRepository;
    @Autowired
    private ResourceCategoryService resourceCategoryService;

    public ResourceCategoryController(ResourceCategoryRepository resourceCategoryRepository){
        super();
        this.resourceCategoryRepository = resourceCategoryRepository;
    }

    @PostMapping()
    public ResponseEntity addResourceCategory(@RequestBody @Valid AddResourceCategoryRequest addResourceCategoryRequest){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return resourceCategoryService.addResourceCategory(auth,addResourceCategoryRequest);
    }
    @PutMapping("{id}")
    public ResponseEntity updateResourceCategory(@RequestBody @Valid AddResourceCategoryRequest addResourceCategoryRequest,
                                                 @PathVariable long id){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return resourceCategoryService.updateResourceCategory(auth,addResourceCategoryRequest,id);
    }
    @DeleteMapping("{id}")
    public ResponseEntity deleteResourceCategory(@PathVariable long id){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return resourceCategoryService.deleteResouceCategory(auth,id);
    }
    @PostMapping("{clientId}/add/{resourceCategoryId}")
    public ResponseEntity addResourceCategoryToClient(@PathVariable long clientId,
                                                      @PathVariable long resourceCategoryId){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return resourceCategoryService.addResourceCategoryToClient(auth,clientId,resourceCategoryId);
    }
    @PostMapping("{clientId}/remove/{resourceCategoryId}")
    public ResponseEntity deleteResourceCategoryFromClient(@PathVariable long clientId,
                                                           @PathVariable long resourceCategoryId){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return resourceCategoryService.removeResourceCategoryFromClient(auth,clientId,resourceCategoryId);
    }

    @GetMapping("/resourceCategory")
    public Iterable<ResourceCategory> resourceCategories(){
        return resourceCategoryRepository.findAll();
    }




}