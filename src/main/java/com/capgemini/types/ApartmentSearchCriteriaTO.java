package com.capgemini.types;

import java.util.Objects;

public class ApartmentSearchCriteriaTO {

    private Double minApartmentSize;
    private Double maxApartmentSize;
    private Integer minRoomNo;
    private Integer maxRoomsNo;
    private Integer minBalconyNo;
    private Integer maxBalconyNo;

    public ApartmentSearchCriteriaTO() {
    }

    public ApartmentSearchCriteriaTO(Double minApartmentSize, Double maxApartmentSize, Integer minRoomNo, Integer maxRoomsNo, Integer minBalconyNo, Integer maxBalconyNo) {
        this.minApartmentSize = minApartmentSize;
        this.maxApartmentSize = maxApartmentSize;
        this.minRoomNo = minRoomNo;
        this.maxRoomsNo = maxRoomsNo;
        this.minBalconyNo = minBalconyNo;
        this.maxBalconyNo = maxBalconyNo;
    }

    public Double getMinApartmentSize() {
        return minApartmentSize;
    }

    public void setMinApartmentSize(Double minApartmentSize) {
        this.minApartmentSize = minApartmentSize;
    }

    public Double getMaxApartmentSize() {
        return maxApartmentSize;
    }

    public void setMaxApartmentSize(Double maxApartmentSize) {
        this.maxApartmentSize = maxApartmentSize;
    }

    public Integer getMinRoomNo() {
        return minRoomNo;
    }

    public void setMinRoomNo(Integer minRoomNo) {
        this.minRoomNo = minRoomNo;
    }

    public Integer getMaxRoomsNo() {
        return maxRoomsNo;
    }

    public void setMaxRoomsNo(Integer maxRoomsNo) {
        this.maxRoomsNo = maxRoomsNo;
    }

    public Integer getMinBalconyNo() {
        return minBalconyNo;
    }

    public void setMinBalconyNo(Integer minBalconyNo) {
        this.minBalconyNo = minBalconyNo;
    }

    public Integer getMaxBalconyNo() {
        return maxBalconyNo;
    }

    public void setMaxBalconyNo(Integer maxBalconyNo) {
        this.maxBalconyNo = maxBalconyNo;
    }

    public static ApartmentSearchCriteriaTOBuilder builder() {
        return new ApartmentSearchCriteriaTOBuilder();
    }

    public static class ApartmentSearchCriteriaTOBuilder {

        private Double minApartmentSize;
        private Double maxApartmentSize;
        private Integer minRoomNo;
        private Integer maxRoomNo;
        private Integer minBalconyNo;
        private Integer maxBalconyNo;

        public ApartmentSearchCriteriaTOBuilder withMinApartmentSize(Double minApartmentSize) {
            this.minApartmentSize = minApartmentSize;
            return this;
        }

        public ApartmentSearchCriteriaTOBuilder withMaxApartmentSize(Double maxApartmentSize) {
            this.maxApartmentSize = maxApartmentSize;
            return this;
        }

        public ApartmentSearchCriteriaTOBuilder withMinRoomNo(Integer minRoomNo) {
            this.minRoomNo = minRoomNo;
            return this;
        }

        public ApartmentSearchCriteriaTOBuilder withMaxRoomNo(Integer maxRoomsNo) {
            this.maxRoomNo = maxRoomsNo;
            return this;
        }

        public ApartmentSearchCriteriaTOBuilder withMinBalconyNo(Integer minBalconyNo) {
            this.minBalconyNo = minBalconyNo;
            return this;
        }

        public ApartmentSearchCriteriaTOBuilder withMaxBalconyNo(Integer minBalconyNo) {
            this.minBalconyNo = minBalconyNo;
            return this;
        }

        public ApartmentSearchCriteriaTO build() {
            return new ApartmentSearchCriteriaTO(minApartmentSize,
                    maxApartmentSize,
                    minRoomNo,
                    maxRoomNo,
                    minBalconyNo,
                    maxBalconyNo);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ApartmentSearchCriteriaTO that = (ApartmentSearchCriteriaTO) o;
        return Objects.equals(minApartmentSize, that.minApartmentSize) &&
                Objects.equals(maxApartmentSize, that.maxApartmentSize) &&
                Objects.equals(minRoomNo, that.minRoomNo) &&
                Objects.equals(maxRoomsNo, that.maxRoomsNo) &&
                Objects.equals(minBalconyNo, that.minBalconyNo) &&
                Objects.equals(maxBalconyNo, that.maxBalconyNo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(minApartmentSize, maxApartmentSize, minRoomNo, maxRoomsNo, minBalconyNo, maxBalconyNo);
    }
}
