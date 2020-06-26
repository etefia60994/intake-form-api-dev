package com.bfl.intakeform.repository;

import com.bfl.intakeform.model.ResourceCategory;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResourceCategoryRepository extends CrudRepository<ResourceCategory, Long > {
    List<ResourceCategory> findByResourceName(String resourceName);

}
