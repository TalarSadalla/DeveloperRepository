package com.capgemini.types;

import org.springframework.util.CollectionUtils;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


public class BuildingTO {

    private int version;
    private Long id;
    private String description;
    private String localization;
    private int floorNo;
    private boolean isElevator;
    private int apartmentNo;

    public BuildingTO(int version, Long id, String description, String localization, int floorNo, boolean isElevator, int apartmentNo) {
        this.version = version;
        this.id = id;
        this.description = description;
        this.localization = localization;
        this.floorNo = floorNo;
        this.isElevator = isElevator;
        this.apartmentNo = apartmentNo;
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

    public int getFloorNo() {
        return floorNo;
    }

    public boolean isElevator() {
        return isElevator;
    }

    public int getApartmentNo() {
        return apartmentNo;
    }

    public static BuildingTOBuilder builder() {
        return new BuildingTOBuilder();
    }

    public static class BuildingTOBuilder {

        private int version;
        private Long id;
        String description;
        String localization;
        int floorNo;
        boolean isElevator;
        int apartmentNo;

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

        public BuildingTOBuilder withFloorNumber(int floorNo) {
            this.floorNo = floorNo;
            return this;
        }

        public BuildingTOBuilder withElevator(boolean isElevator) {
            this.isElevator = isElevator;
            return this;
        }

        public BuildingTOBuilder withApartmentNumber(int apartmentNo) {
            this.apartmentNo = apartmentNo;
            return this;
        }

        public BuildingTOBuilder withId(Long id) {
            this.id = id;
            return this;
        }

        public BuildingTOBuilder withId(int version) {
            this.version = version;
            return this;
        }

        public BuildingTO build() {
            checkBeforeBuild(description, localization, floorNo, isElevator, apartmentNo);
            return new BuildingTO(version, id, description, localization, floorNo, isElevator, apartmentNo);
        }

        private void checkBeforeBuild(String description, String localization, int floorNo, boolean isElevator, int apartmentNo) {
            if (description == null || description.isEmpty() || localization == null || localization.isEmpty() || floorNo < 0 || floorNo > 50 || apartmentNo < 0 || apartmentNo > 150) {
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
                '}';
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((description == null) ? 0 : description.hashCode());
        result = prime * result + ((localization == null) ? 0 : localization.hashCode());
        result = prime * result + ((floorNo < 0 || floorNo > 50) ? 0 : Integer.hashCode(floorNo));
        result = prime * result + ((apartmentNo < 0 || apartmentNo > 150) ? 0 : Integer.hashCode(apartmentNo));
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        BuildingTO other = (BuildingTO) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (description == null) {
            if (other.description != null)
                return false;
        } else if (!description.equals(other.description))
            return false;
        if (localization == null) {
            if (other.localization != null)
                return false;
        } else if (!localization.equals(other.localization))
            return false;
        if (floorNo < 0 || floorNo > 50) {
            if (other.floorNo < 0 || other.floorNo > 50)
                return false;
        } else if (floorNo != other.floorNo)
            return false;
        if (apartmentNo < 0 || apartmentNo > 150) {
            if (other.apartmentNo < 0 || other.apartmentNo > 150)
                return false;
        } else if (apartmentNo != other.apartmentNo)
            return false;
        return true;
    }
}
