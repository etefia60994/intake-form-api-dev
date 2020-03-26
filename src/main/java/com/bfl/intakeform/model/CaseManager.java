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
@Table(name="CaseManager")
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

    @Getter
    @OneToMany
    private final List<Client> clients = new ArrayList<>();
}
