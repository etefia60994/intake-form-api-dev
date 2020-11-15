package com.bfl.intakeform.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name="CaseManager", uniqueConstraints = {
        @UniqueConstraint(columnNames = {
                "userName"
        })})
public class CaseManager {

    @Getter
    @Id
    @GeneratedValue
    private long id;

    @Getter @Setter
    @NotBlank
    @Column(name="FirstName")
    private String firstName;

    @Getter @Setter
    @NotBlank
    @Column(name="LastName")
    private String lastName;

    @Getter @Setter
    @NotBlank
    private String userName;

    @Getter @Setter
    @NotBlank
    private String password;

    @Enumerated(EnumType.ORDINAL)
    @Getter @Setter
    private CaseManagerRoles caseManagerRole;

    @Enumerated(EnumType.ORDINAL)
    @Getter @Setter
    private CaseManagerStatus caseManagerStatus;


    @Getter
    @OneToMany
    private final List<Client> clients = new ArrayList<>();
}
