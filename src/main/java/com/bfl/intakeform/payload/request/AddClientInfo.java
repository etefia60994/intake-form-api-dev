package com.bfl.intakeform.payload.request;



import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import javax.validation.constraints.*;
import java.util.List;

/**
 * public class that consists of all the information about a client
 *
 * **/

public class AddClientInfo {


    @NotBlank
    @Getter@Setter
    private String firstName;

@Getter@Setter
    @NotBlank
    private String lastName;

    @Getter@Setter
    @NotBlank
    private String address;

    @Getter@Setter
    @NotBlank
    @NotNull
    private int zip;

    @Getter@Setter
    @NotEmpty
    @NotBlank
    @Size(max = 5)
    private String state;

    @NotBlank
    @Getter@Setter
    private String maritalStatus;

    @NotBlank
    @Getter@Setter
    @Size(max = 10)
    private String sex;

    @NotBlank
    @Getter@Setter
    private String race;

    @NotBlank
    @Positive
    @Getter@Setter
    private int age;

    @Getter@Setter
    List<Long> serviceProvidersIds;
}
