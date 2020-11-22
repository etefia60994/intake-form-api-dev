package com.bfl.intakeform.payload.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.*;

public class AddServiceProviderRequest {
    @Getter
    @Setter
    @NotBlank
    private String name;

    @Setter@Getter
    @Email
    private String email;
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
