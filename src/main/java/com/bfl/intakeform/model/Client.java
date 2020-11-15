package com.bfl.intakeform.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDate;



@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data
@Table
public class Client extends AbstractContactInfo {

    @Column(name="IntakeDate")
    final LocalDate intakeDate= LocalDate.now();

    @NotBlank
    @Column(name="MaritalStatus")
    private String maritalStatus;

    @NotBlank
    @Size(max = 10)
    @Column(name="Sex")
    private String sex;

    @NotBlank
    @Column(name="Race")
    private String race;

    @NotBlank
    @Positive
    @Column(name="Age")
    private int age;

    @ManyToOne
    @NotBlank
    private CaseManager caseManager;

    //@ManyToMany
   // private final List<ResourceCategory> resourceCategories = new ArrayList<ResourceCategory>().stream().distinct().collect(Collectors.toList());


   // @ManyToMany
   // private final List<ServiceProvider> serviceProviders = new ArrayList<>();


}
