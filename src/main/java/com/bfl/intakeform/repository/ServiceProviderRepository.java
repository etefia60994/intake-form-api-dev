package com.bfl.intakeform.repository;

import com.bfl.intakeform.model.ResourceCategory;
import com.bfl.intakeform.model.ServiceProvider;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


//Storage for Service Provider Objects
@Repository
public interface ServiceProviderRepository extends CrudRepository<ServiceProvider, Long> {
    //Find by Contact first/last name & Order by First Name
   // List<ServiceProvider> findByFirstNameOrLastNameOrderByFirstNameAsc(String firstName, String lastName);

    //Find by Resource category & Display by Company Name
  //  Page<ServiceProvider> findByResourceCategoriesOrderByServiceProviderName(String resourceCategoryName, Pageable pageable);

}
