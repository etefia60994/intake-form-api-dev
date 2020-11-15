package com.bfl.intakeform.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table
public class ServiceProvider extends AbstractContactInfo {

    @NotBlank
    private String serviceProviderName;

    @Email
    private String email;

   // @ManyToMany
   // private final List<Client> clients = new ArrayList<>();

   // @ManyToMany
   // private final List<ResourceCategory> resourceCategories = new ArrayList<ResourceCategory>().stream().distinct().collect(Collectors.toList());



}
