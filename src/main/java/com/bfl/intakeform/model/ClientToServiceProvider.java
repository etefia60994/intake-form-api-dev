package com.bfl.intakeform.model;

import lombok.*;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(
        uniqueConstraints = { @UniqueConstraint( columnNames = { "client_id", "service_provider_id" } ) } )
public class ClientToServiceProvider {
    @Getter
    @Id
    @GeneratedValue
    private long id;

    @OneToOne
    @Getter@Setter
    private Client client;

    @OneToOne
    @Getter@Setter
    private ServiceProvider serviceProvider;
}
