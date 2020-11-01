package com.bfl.intakeform.payload.request;



import javax.validation.constraints.*;
/**
 * puclic class that consists of all the information about a client
 *
 * **/

public class AddClientInfo {


    @NotBlank
    private String firstName;


    @NotBlank
    private String lastName;

    @NotBlank
    private String address;

    @NotBlank
    @NotNull
    private int zip;

    @NotEmpty
    @NotBlank
    @Size(max = 5)
    private String state;

    @NotBlank
    private String maritalStatus;

    @NotBlank
    @Size(max = 10)
    private String sex;

    @NotBlank
    private String race;

    @NotBlank
    @Positive
    private int age;
}
