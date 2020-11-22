package com.bfl.intakeform.repository;

import com.bfl.intakeform.model.Client;
import com.bfl.intakeform.model.ClientToResourceCategory;
import com.bfl.intakeform.model.ResourceCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientToResourceCategoryRepository extends JpaRepository<ClientToResourceCategory,Long> {
    boolean existsByClientAndResourceCategory(Client client, ResourceCategory resourceCategory);
    @Modifying
    int deleteByClientAndResourceCategory(Client client,ResourceCategory resourceCategory);
}
