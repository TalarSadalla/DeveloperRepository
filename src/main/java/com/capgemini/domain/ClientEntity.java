package com.capgemini.domain;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "CLIENT")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@EntityListeners({OnCreateListener.class, OnUpdateListener.class})
public class ClientEntity extends AbstractEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "CLIENT_ID")
    private Long id;

    @Column(nullable = false)
    String firstName;

    @Column(nullable = false)
    String lastName;

    @Column(nullable = false)
    String phoneNumber;

    @Column(nullable = false)
    String address;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "apartments_customers",
            joinColumns = {@JoinColumn(name = "APARTMENT_ID", nullable = false, updatable = false)},
            inverseJoinColumns = {@JoinColumn(name = "CLIENT_ID", nullable = false, updatable = false)}
    )
    private Set<ClientEntity> apartmentEntitySet = new HashSet<>();
}
