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

    @Version
    private int version;

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
    @JoinColumn(name = "buildingEntity")
    private BuildingEntity buildingEntity;

    @ManyToMany
    Set<ClientEntity> clientEntitySet = new HashSet<>();

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getApartmentSize() {
        return apartmentSize;
    }

    public void setApartmentSize(double apartmentSize) {
        this.apartmentSize = apartmentSize;
    }

    public int getRoomNo() {
        return roomNo;
    }

    public void setRoomNo(int roomNo) {
        this.roomNo = roomNo;
    }

    public int getBalconyNo() {
        return balconyNo;
    }

    public void setBalconyNo(int balconyNo) {
        this.balconyNo = balconyNo;
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getApartmentPrice() {
        return apartmentPrice;
    }

    public void setApartmentPrice(double apartmentPrice) {
        this.apartmentPrice = apartmentPrice;
    }

    public BuildingEntity getBuildingEntity() {
        return buildingEntity;
    }

    public void setBuildingEntity(BuildingEntity buildingEntity) {
        this.buildingEntity = buildingEntity;
    }

    public Set<ClientEntity> getClientEntitySet() {
        return clientEntitySet;
    }

    public void setClientEntitySet(Set<ClientEntity> clientEntitySet) {
        this.clientEntitySet = clientEntitySet;
    }
}
