package com.bfl.intakeform.model;


import lombok.*;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(
        uniqueConstraints = { @UniqueConstraint( columnNames = { "client_id", "resource_category_id" } ) } )
public class ClientToResourceCategory {
    @Getter
    @Id
    @GeneratedValue
    private long id;

    @OneToOne
    @Getter
    @Setter
    private Client client;

    @OneToOne
    @Getter
    @Setter
    private ResourceCategory resourceCategory;
}
