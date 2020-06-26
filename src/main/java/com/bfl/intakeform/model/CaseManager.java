package com.bfl.intakeform.model;

import lombok.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name="CaseManager")
public class CaseManager {


    @Id
    @GeneratedValue
    @Getter
    private int id;

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
    @Column(name="username")
    private String username;

    @Setter
    @NotBlank
    @Column(name="pwHash")
    private String pwHash;

    @Getter
    @OneToMany
    private final List<Client> clients = new ArrayList<>();

    public CaseManager(String username, String password,String firstName,String lastName) {
        this.username = username;
        this.pwHash = encoder.encode(password);
        this.firstName=firstName;
        this.lastName=lastName;
    }

    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public boolean isMatchingPassword(String password) {
        return encoder.matches(password, pwHash);
    }

}
