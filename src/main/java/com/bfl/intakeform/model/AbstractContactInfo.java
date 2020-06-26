package com.bfl.intakeform.model;

import lombok.*;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@ToString
@EqualsAndHashCode
@MappedSuperclass
abstract class AbstractContactInfo {


    @Getter
    @Id
    @GeneratedValue
    private long id;

    @Getter @Setter
    @NotBlank
    private String firstName;

    @Getter @Setter
    @NotBlank
    private String lastName;

    @Getter @Setter
    @NotBlank
    private String address;

    @Getter @Setter
    @NotBlank
    @NotNull
    private int zip;

    @Getter @Setter
    @NotEmpty
    @NotBlank
    @Size(max = 5)
    private String state;
}
