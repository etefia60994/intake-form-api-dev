package com.bfl.intakeform.payload.response;

import com.bfl.intakeform.model.CaseManagerRoles;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
@AllArgsConstructor
public class AuthenticationResponse {
    @Getter@Setter
    private String accessToken;
    @Getter@Setter
    private String tokenType;
    @Getter@Setter
    private CaseManagerRoles roleType;




}
