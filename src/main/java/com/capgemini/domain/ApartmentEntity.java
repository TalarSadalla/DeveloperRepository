package com.capgemini.domain;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "APARTMENT")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@EntityListeners({OnCreateListener.class, OnUpdateListener.class})
public class ApartmentEntity extends AbstractEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "APARTMENT_ID")
    private Long id;

    @Column
    double apartmentSize;

    @Column
    int roomNo;

    @Column
    int balconyNo;

    @Column(nullable = false)
    int floor;

    @Column(nullable = false)
    String address;

    @Column(nullable = false)
    String status;

    @Column(nullable = false)
    double apartmentPrice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BUILDING")
    private BuildingEntity buildingEntity;

    @ManyToMany
    Set<ClientEntity> clientEntitySet = new HashSet<>();
}
