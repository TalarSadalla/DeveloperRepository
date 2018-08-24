package com.capgemini.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.Table;
import javax.persistence.InheritanceType;
import java.io.Serializable;

@Entity
@Table(name = "apartments")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@EntityListeners({OnCreateListener.class, OnUpdateListener.class})
public class ApartmentEntity extends AbstractEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private Double apartmentSize;

    @Column
    private Integer roomNo;

    @Column
    private Integer balconyNo;

    @Column(nullable = false)
    private Integer floor;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String status;

    @Column(nullable = false)
    private Double apartmentPrice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "building")
    private BuildingEntity building;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getApartmentSize() {
        return apartmentSize;
    }

    public void setApartmentSize(Double apartmentSize) {
        this.apartmentSize = apartmentSize;
    }

    public Integer getRoomNo() {
        return roomNo;
    }

    public void setRoomNo(Integer roomNo) {
        this.roomNo = roomNo;
    }

    public Integer getBalconyNo() {
        return balconyNo;
    }

    public void setBalconyNo(Integer balconyNo) {
        this.balconyNo = balconyNo;
    }

    public Integer getFloor() {
        return floor;
    }

    public void setFloor(Integer floor) {
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

    public Double getApartmentPrice() {
        return apartmentPrice;
    }

    public void setApartmentPrice(Double apartmentPrice) {
        this.apartmentPrice = apartmentPrice;
    }

    public BuildingEntity getBuildingEntity() {
        return building;
    }

    public void setBuildingEntity(BuildingEntity building) {
        this.building = building;
    }

}
