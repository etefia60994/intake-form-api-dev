package com.bfl.intakeform.model;

import com.bfl.intakeform.payload.request.AddClientInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
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



    public void requestSetter(AddClientInfo addClientInfo, CaseManager caseManager){
        this.setCaseManager(caseManager);
        this.setAge(addClientInfo.getAge());
        this.setRace(addClientInfo.getRace());
        this.setSex(addClientInfo.getSex());
        this.setAddress(addClientInfo.getAddress());
        this.setMaritalStatus(addClientInfo.getMaritalStatus());
        this.setFirstName(addClientInfo.getFirstName());
        this.setLastName(addClientInfo.getLastName());
        this.setState(addClientInfo.getState());
        this.setZip(addClientInfo.getZip());
    }

    //@ManyToMany
   // private final List<ResourceCategory> resourceCategories = new ArrayList<ResourceCategory>().stream().distinct().collect(Collectors.toList());


   // @OneToMany
   // private final List<ServiceProvider> serviceProviders = new ArrayList<>();


}
