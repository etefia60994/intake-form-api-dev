package com.bfl.intakeform.repository;

import com.bfl.intakeform.model.ResourceCategory;
import com.bfl.intakeform.model.ServiceProvider;
import com.bfl.intakeform.model.ServiceProviderToResourceCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceProviderToResourceCategoryRepository extends JpaRepository<ServiceProviderToResourceCategory,Long> {
  boolean existsByServiceProviderAndResourceCategory(ServiceProvider serviceProvider, ResourceCategory resourceCategory);

   @Modifying
   int deleteByServiceProviderAndResourceCategory(ServiceProvider serviceProvider,ResourceCategory resourceCategory);
}
