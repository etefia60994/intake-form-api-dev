package com.bfl.intakeform.payload.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

public class UpdateCaseManagerNoPasswordRequest {
    @Getter
    @Setter
    @NotBlank
    private String firstName;

    @Getter @Setter
    @NotBlank
    private String lastName;

    @Getter @Setter
    @NotBlank
    private String userName;

}
