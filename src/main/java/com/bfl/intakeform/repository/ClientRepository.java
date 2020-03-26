package com.bfl.intakeform.repository;

import com.bfl.intakeform.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

//Stores Client Objects
public interface ClientRepository extends JpaRepository<Client, Long> {
    //Orders clients by first or last name
    List<Client> findByFirstNameOrLastNameOrderByLastNameDesc(String firstName, String lastName);


}
