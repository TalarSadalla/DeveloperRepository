package com.capgemini.types;


import java.util.Objects;

public class ApartmentTO {

    private Long version;

    private Long id;

    private Double apartmentSize;

    private Integer roomNo;

    private Integer balconyNo;

    private Integer floor;

    private String address;

    private String status;

    private Double apartmentPrice;

    private Long buildingId;

    public ApartmentTO() {
    }

    public ApartmentTO(Long version, Long id, Double apartmentSize, Integer roomNo, Integer balconyNo, Integer floor, String address, String status, Double apartmentPrice, Long buildingId) {
        this.version = version;
        this.id = id;
        this.apartmentSize = apartmentSize;
        this.roomNo = roomNo;
        this.balconyNo = balconyNo;
        this.floor = floor;
        this.address = address;
        this.status = status;
        this.apartmentPrice = apartmentPrice;
        this.buildingId = buildingId;
    }

    public Long getVersion() {
        return version;
    }

    public Long getId() {
        return id;
    }

    public Double getApartmentSize() {
        return apartmentSize;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setApartmentSize(Double apartmentSize) {
        this.apartmentSize = apartmentSize;
    }

    public void setRoomNo(Integer roomNo) {
        this.roomNo = roomNo;
    }

    public void setBalconyNo(Integer balconyNo) {
        this.balconyNo = balconyNo;
    }

    public void setFloor(Integer floor) {
        this.floor = floor;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setApartmentPrice(Double apartmentPrice) {
        this.apartmentPrice = apartmentPrice;
    }

    public void setBuildingId(Long buildingId) {
        this.buildingId = buildingId;
    }

    public Integer getRoomNo() {
        return roomNo;
    }

    public Integer getBalconyNo() {
        return balconyNo;
    }

    public Integer getFloor() {
        return floor;
    }

    public String getAddress() {
        return address;
    }

    public String getStatus() {
        return status;
    }

    public Double getApartmentPrice() {
        return apartmentPrice;
    }

    public Long getBuildingId() {
        return buildingId;
    }

    public static ApartmentTOBuilder builder() {
        return new ApartmentTOBuilder();
    }

    public static class ApartmentTOBuilder {

        private Long version;
        private Long id;
        private Double apartmentSize;
        private Integer roomNo;
        private Integer balconyNo;
        private Integer floor;
        private String address;
        private String status;
        private Double apartmentPrice;
        private Long buildingId;


        public ApartmentTOBuilder() {
            super();
        }

        public ApartmentTOBuilder withApartmentSize(Double apartmentSize) {
            this.apartmentSize = apartmentSize;
            return this;
        }

        public ApartmentTOBuilder withRoomNo(Integer roomNo) {
            this.roomNo = roomNo;
            return this;
        }

        public ApartmentTOBuilder withBalconyNo(Integer balconyNo) {
            this.balconyNo = balconyNo;
            return this;
        }

        public ApartmentTOBuilder withFloor(Integer floor) {
            this.floor = floor;
            return this;
        }

        public ApartmentTOBuilder withAddress(String address) {
            this.address = address;
            return this;
        }

        public ApartmentTOBuilder withStatus(String status) {
            this.status = status;
            return this;
        }

        public ApartmentTOBuilder withApartmentPrice(Double apartmentPrice) {
            this.apartmentPrice = apartmentPrice;
            return this;
        }

        public ApartmentTOBuilder withBuildingId(Long buildingId) {
            this.buildingId = buildingId;
            return this;
        }

        public ApartmentTOBuilder withId(Long id) {
            this.id = id;
            return this;
        }

        public ApartmentTOBuilder withVersionId(Long version) {
            this.version = version;
            return this;
        }


        public ApartmentTO build() {
            checkBeforeBuild(apartmentSize, roomNo, balconyNo, floor, address, status, apartmentPrice, buildingId );
            return new ApartmentTO(version, id, apartmentSize, roomNo, balconyNo, floor, address, status, apartmentPrice, buildingId);
        }

        private void checkBeforeBuild(Double apartmentSize, Integer roomNo, Integer balconyNo, Integer floor, String address, String status, Double apartmentPrice, Long buildingId) {
            if (apartmentSize < 0 ||
                    apartmentSize > 500 || apartmentSize == null ||
                    roomNo < 0 || roomNo > 40 || roomNo == null ||
                    balconyNo < 0 || balconyNo > 20 || balconyNo == null ||
                    floor < 0 || floor > 5 || floor == null ||
                    address.isEmpty() || address == null ||
                    status.isEmpty() || status == null ||
                    apartmentPrice < 0 || apartmentPrice > 100000000 || apartmentPrice == null ||
                    buildingId < 0 || buildingId == null) {
                throw new RuntimeException("Incorrect apartment to be created");
            }
        }
    }

    @Override
    public String toString() {
        return "ApartmentTO{" +
                "version=" + version +
                ", id=" + id +
                ", apartmentSize=" + apartmentSize +
                ", roomNo=" + roomNo +
                ", balconyNo=" + balconyNo +
                ", floor=" + floor +
                ", address='" + address + '\'' +
                ", status='" + status + '\'' +
                ", apartmentPrice=" + apartmentPrice +
                ", buildingId=" + buildingId +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ApartmentTO that = (ApartmentTO) o;
        return Objects.equals(version, that.version) &&
                Objects.equals(id, that.id) &&
                Objects.equals(apartmentSize, that.apartmentSize) &&
                Objects.equals(roomNo, that.roomNo) &&
                Objects.equals(balconyNo, that.balconyNo) &&
                Objects.equals(floor, that.floor) &&
                Objects.equals(address, that.address) &&
                Objects.equals(status, that.status) &&
                Objects.equals(apartmentPrice, that.apartmentPrice) &&
                Objects.equals(buildingId, that.buildingId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(version, id, apartmentSize, roomNo, balconyNo, floor, address, status, apartmentPrice, buildingId);
    }
}
