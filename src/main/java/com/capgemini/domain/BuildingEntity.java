package com.capgemini.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.CascadeType;
import javax.persistence.OneToMany;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.Table;
import javax.persistence.InheritanceType;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Building Entity
 */
@Entity
@Table(name = "buildings")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@EntityListeners({OnCreateListener.class, OnUpdateListener.class})
public class BuildingEntity extends AbstractEntity implements Serializable {
    private static final long serialVersionUID = 1L;


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    String description;

    @Column(nullable = false)
    String localization;

    @Column(nullable = false)
    int floorNo;

    @Column(nullable = false)
    boolean isElevator;

    @Column(nullable = false)
    int apartmentNo;

    @OneToMany(mappedBy = "building", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    List<ApartmentEntity> listOfApartments = new ArrayList<>();


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocalization() {
        return localization;
    }

    public void setLocalization(String localization) {
        this.localization = localization;
    }

    public int getFloorNo() {
        return floorNo;
    }

    public void setFloorNo(int floorNo) {
        this.floorNo = floorNo;
    }

    public boolean isElevator() {
        return isElevator;
    }

    public void setElevator(boolean elevator) {
        isElevator = elevator;
    }

    public int getApartmentNo() {
        return apartmentNo;
    }

    public void setApartmentNo(int apartmentNo) {
        this.apartmentNo = apartmentNo;
    }

    public List<ApartmentEntity> getListOfApartments() {
        return listOfApartments;
    }

    public void setListOfApartments(List<ApartmentEntity> listOfApartments) {
        this.listOfApartments = listOfApartments;
    }
}
