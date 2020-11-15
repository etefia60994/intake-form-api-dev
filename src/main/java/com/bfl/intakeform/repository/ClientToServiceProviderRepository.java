package com.bfl.intakeform.repository;

import com.bfl.intakeform.model.Client;
import com.bfl.intakeform.model.ClientToServiceProvider;
import com.bfl.intakeform.model.ServiceProvider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientToServiceProviderRepository extends JpaRepository<ClientToServiceProvider,Long> {

    void deleteByServiceProviderAndClient(ServiceProvider serviceProvider, Client client);
}
