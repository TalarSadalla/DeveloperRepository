package com.capgemini.types;


import java.util.HashSet;
import java.util.Set;

public class ApartmentTO {

    private int version;

    private Long id;

    private double apartmentSize;

    private int roomNo;

    private int balconyNo;

    private int floor;

    private String address;

    private String status;

    private double apartmentPrice;

    private Long buildingId;

    Set<Long> clientIdSet = new HashSet<>();

    public ApartmentTO(int version, Long id, double apartmentSize, int roomNo, int balconyNo, int floor, String address, String status, double apartmentPrice, Long buildingId, Set<Long> clientIdSet) {
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
        this.clientIdSet = clientIdSet;
    }

    public int getVersion() {
        return version;
    }

    public Long getId() {
        return id;
    }

    public double getApartmentSize() {
        return apartmentSize;
    }

    public int getRoomNo() {
        return roomNo;
    }

    public int getBalconyNo() {
        return balconyNo;
    }

    public int getFloor() {
        return floor;
    }

    public String getAddress() {
        return address;
    }

    public String getStatus() {
        return status;
    }

    public double getApartmentPrice() {
        return apartmentPrice;
    }

    public Long getBuildingId() {
        return buildingId;
    }

    public Set<Long> getClientIdSet() {
        return clientIdSet;
    }

    public static ApartmentTOBuilder builder() {
        return new ApartmentTOBuilder();
    }

    public static class ApartmentTOBuilder {

        private int version;
        private Long id;
        private double apartmentSize;
        private int roomNo;
        private int balconyNo;
        private int floor;
        private String address;
        private String status;
        private double apartmentPrice;
        private Long buildingId;
        private Set<Long> clientIdSet = new HashSet<>();


        public ApartmentTOBuilder() {
            super();
        }

        public ApartmentTOBuilder withapartmentSize(double apartmentSize) {
            this.apartmentSize = apartmentSize;
            return this;
        }

        public ApartmentTOBuilder withRoomNo(int roomNo) {
            this.roomNo = roomNo;
            return this;
        }

        public ApartmentTOBuilder withBalconyNo(int balconyNo) {
            this.balconyNo = balconyNo;
            return this;
        }

        public ApartmentTOBuilder withFloor(int floor) {
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

        public ApartmentTOBuilder withApartmentPrice(double apartmentPrice) {
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

        public ApartmentTOBuilder withId(int version) {
            this.version = version;
            return this;
        }

        public ApartmentTOBuilder withClientIdSet(Set<Long> clientIdSet) {
            this.clientIdSet = clientIdSet;
            return this;
        }

        public ApartmentTO build() {
            checkBeforeBuild(version, id, apartmentSize, roomNo, balconyNo, floor, address, status, apartmentPrice, buildingId, clientIdSet);
            return new ApartmentTO(version, id, apartmentSize, roomNo, balconyNo, floor, address, status, apartmentPrice, buildingId, clientIdSet);
        }

        private void checkBeforeBuild(int version, Long id, double apartmentSize, int roomNo, int balconyNo, int floor, String address, String status, double apartmentPrice, Long buildingId, Set<Long> clientIdSet) {
            if (apartmentSize < 0 || apartmentSize > 500 || roomNo < 0 || roomNo > 40 || balconyNo < 0 || balconyNo > 20 || floor < 0 || floor > 5 || address.isEmpty() || address == null || status.isEmpty() || status == null || apartmentPrice < 0 || apartmentPrice > 100000000 || buildingId < 0 || buildingId==null || clientIdSet.isEmpty() || clientIdSet == null) {
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
                ", clientIdSet=" + clientIdSet +
                '}';
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((apartmentSize < 0 || apartmentSize > 500) ? 0 : Double.hashCode(apartmentSize));
        result = prime * result + ((roomNo < 0 || roomNo > 40) ? 0 : Integer.hashCode(roomNo));
        result = prime * result + ((balconyNo < 0 || balconyNo > 20) ? 0 : Integer.hashCode(balconyNo));
        result = prime * result + ((floor < 0 || floor > 5) ? 0 : Integer.hashCode(floor));
        result = prime * result + ((address == null) ? 0 : address.hashCode());
        result = prime * result + ((status == null) ? 0 : status.hashCode());
        result = prime * result + ((apartmentPrice < 0 || apartmentPrice > 100000000) ? 0 : Double.hashCode(apartmentSize));
        result = prime * result + ((buildingId <0) ? 0 : buildingId.hashCode());
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
        ApartmentTO other = (ApartmentTO) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (address == null) {
            if (other.address != null)
                return false;
        } else if (!address.equals(other.address))
            return false;

        return true;
    }
}
