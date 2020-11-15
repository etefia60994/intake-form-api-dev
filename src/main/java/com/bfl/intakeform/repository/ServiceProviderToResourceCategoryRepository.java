package com.bfl.intakeform.repository;

import com.bfl.intakeform.model.ServiceProviderToResourceCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceProviderToResourceCategoryRepository extends JpaRepository<ServiceProviderToResourceCategory,Long> {
}
