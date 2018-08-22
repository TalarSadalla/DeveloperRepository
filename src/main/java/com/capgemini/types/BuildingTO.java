package com.capgemini.types;

import com.capgemini.domain.ApartmentEntity;
import org.springframework.util.CollectionUtils;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;


public class BuildingTO {

    private int version;
    private Long id;
    private String description;
    private String localization;
    private Integer floorNo;
    private boolean isElevator;
    private Integer apartmentNo;
    private Set<Long> listOfApartments = new HashSet<>();

    public BuildingTO() {
    }

    public BuildingTO(int version, Long id, String description, String localization, Integer floorNo, boolean isElevator, Integer apartmentNo, Set<Long> listOfApartments) {
        this.version = version;
        this.id = id;
        this.description = description;
        this.localization = localization;
        this.floorNo = floorNo;
        this.isElevator = isElevator;
        this.apartmentNo = apartmentNo;
        this.listOfApartments = listOfApartments;
    }

    public int getVersion() {
        return version;
    }

    public Long getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public String getLocalization() {
        return localization;
    }

    public Integer getFloorNo() {
        return floorNo;
    }

    public boolean isElevator() {
        return isElevator;
    }

    public Integer getApartmentNo() {
        return apartmentNo;
    }

    public Set<Long> getListOfApartments() {
        return listOfApartments;
    }

    public static BuildingTOBuilder builder() {
        return new BuildingTOBuilder();
    }

    public static class BuildingTOBuilder {

        private int version;
        private Long id;
        String description;
        String localization;
        Integer floorNo;
        boolean isElevator;
        Integer apartmentNo;
        private Set<Long> listOfApartments;


        public BuildingTOBuilder() {
            super();
        }

        public BuildingTOBuilder withDescription(String description) {
            this.description = description;
            return this;
        }

        public BuildingTOBuilder withLocalization(String localization) {
            this.localization = localization;
            return this;
        }

        public BuildingTOBuilder withFloorNumber(Integer floorNo) {
            this.floorNo = floorNo;
            return this;
        }

        public BuildingTOBuilder withElevator(boolean isElevator) {
            this.isElevator = isElevator;
            return this;
        }

        public BuildingTOBuilder withApartmentNumber(Integer apartmentNo) {
            this.apartmentNo = apartmentNo;
            return this;
        }

        public BuildingTOBuilder withId(Long id) {
            this.id = id;
            return this;
        }

        public BuildingTOBuilder withVersionId(int version) {
            this.version = version;
            return this;
        }

        public BuildingTOBuilder withListOfApartments(Set<Long> listOfApartments) {
            this.listOfApartments = listOfApartments;
            return this;
        }

        public BuildingTO build() {
            checkBeforeBuild(description, localization, floorNo, isElevator, apartmentNo, listOfApartments);
            return new BuildingTO(version, id, description, localization, floorNo, isElevator, apartmentNo, listOfApartments);
        }

        private void checkBeforeBuild(String description, String localization, Integer floorNo, boolean isElevator, Integer apartmentNo, Set<Long> listOfApartments) {
            if (description == null || description.isEmpty() ||
                    localization == null || localization.isEmpty() ||
                    floorNo < 0 || floorNo > 50 || floorNo == null ||
                    apartmentNo < 0 || apartmentNo > 150 || apartmentNo == null ||
                    listOfApartments == null) {
                throw new RuntimeException("Incorrect building to be created");
            }
        }
    }

    @Override
    public String toString() {
        return "BuildingTO{" +
                "version=" + version +
                ", id=" + id +
                ", description='" + description + '\'' +
                ", localization='" + localization + '\'' +
                ", floorNo=" + floorNo +
                ", isElevator=" + isElevator +
                ", apartmentNo=" + apartmentNo +
                ", listOfApartments=" + listOfApartments +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BuildingTO that = (BuildingTO) o;
        return version == that.version &&
                floorNo == that.floorNo &&
                isElevator == that.isElevator &&
                apartmentNo == that.apartmentNo &&
                Objects.equals(id, that.id) &&
                Objects.equals(description, that.description) &&
                Objects.equals(localization, that.localization) &&
                Objects.equals(listOfApartments, that.listOfApartments);
    }

    @Override
    public int hashCode() {
        return Objects.hash(version, id, description, localization, floorNo, isElevator, apartmentNo, listOfApartments);
    }
}
