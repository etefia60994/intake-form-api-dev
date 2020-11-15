package com.bfl.intakeform.model;

import lombok.*;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(
        uniqueConstraints = { @UniqueConstraint( columnNames = { "service_provider_id", "resource_category_id" } ) } )
public class ServiceProviderToResourceCategory {


    @Getter
    @Id
    @GeneratedValue
    private long id;

    @OneToOne
    @Getter
    @Setter
    private ServiceProvider serviceProvider;

    @OneToOne
    @Getter
    @Setter
    private ResourceCategory resourceCategory;
}
