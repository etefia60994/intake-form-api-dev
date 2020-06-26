package com.bfl.intakeform.repository;

import com.bfl.intakeform.model.CaseManager;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

@Repository
public interface CaseManagerRepository extends CrudRepository<CaseManager, Integer> {
    List<CaseManager> findByFirstNameOrLastNameOrderByFirstNameAsc(String firstName,String lastName);

    CaseManager findByUsername(String username);
}
