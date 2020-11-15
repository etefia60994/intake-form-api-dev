package com.bfl.intakeform.payload.request;

import com.bfl.intakeform.model.CaseManagerRoles;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;

public class AddCaseManagerRequest {
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

    @Getter @Setter
    @NotBlank
    private String password;

}
