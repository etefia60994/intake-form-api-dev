package com.bfl.intakeform.payload.request;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;


public class AuthenticationRequest {
    @NotBlank
    @Getter@Setter
    private String username;
    @NotBlank@Getter@Setter
    private String password;

}
