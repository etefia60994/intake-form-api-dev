package com.bfl.intakeform.model;

import lombok.*;
import org.springframework.stereotype.Service;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Service
@Entity
@Table
public class ResourceCategory {

    @Getter
    @Id
    @GeneratedValue
    @Column(name="id")
    private long id;

    @Getter @Setter
    @Column(name="resourceName")
    private String resourceName;

    @ManyToMany
    private final List<ServiceProvider> serviceProviderList = new ArrayList<>();

}
