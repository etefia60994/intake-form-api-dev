package com.bfl.intakeform.payload.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class AddServiceProviderRequest {
    @Getter
    @Setter
    @NotBlank
    private String name;

    @Setter@Getter
    @Email
    private String email;
}
