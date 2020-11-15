package com.bfl.intakeform.repository;

import com.bfl.intakeform.model.CaseManager;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CaseManagerRepository extends CrudRepository<CaseManager, Long> {
    List<CaseManager> findByFirstNameOrLastNameOrderByFirstNameAsc(String firstName,String lastName);
    Optional<CaseManager> findByUserName(String userName);

    boolean existsByUserName(String userName);
}
