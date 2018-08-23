package com.capgemini.types;

import java.util.*;

public class ClientTO {

    private Long version;
    private Long id;
    String firstName;
    String lastName;
    String phoneNumber;
    String address;
    private List<Long> apartmentIdSet = new ArrayList<>();

    public ClientTO(Long version, Long id, String firstName, String lastName, String phoneNumber, String address, List<Long> apartmentIdSet) {
        this.version = version;
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.apartmentIdSet = apartmentIdSet;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setApartmentIdSet(List<Long> apartmentIdSet) {
        this.apartmentIdSet = apartmentIdSet;
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public List<Long> getApartmentIdSet() {
        return apartmentIdSet;
    }

    public static ClientTOBuilder builder() {
        return new ClientTOBuilder();
    }

    public static class ClientTOBuilder {

        private Long version;
        private Long id;
        private String firstName;
        private String lastName;
        private String phoneNumber;
        private String address;
        private List<Long> apartmentIdSet;

        public ClientTOBuilder withVersionId(Long version) {
            this.version = version;
            return this;
        }

        public ClientTOBuilder withId(Long id) {
            this.id = id;
            return this;
        }

        public ClientTOBuilder withFirstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public ClientTOBuilder withLastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public ClientTOBuilder withPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
            return this;
        }

        public ClientTOBuilder withAddress(String address) {
            this.address = address;
            return this;
        }

        public ClientTOBuilder withApartmentIds(List<Long> apartmentIdSet) {
            this.apartmentIdSet = apartmentIdSet;
            return this;
        }

        public ClientTO build() {
            checkBeforeBuild(firstName, lastName, phoneNumber, address, apartmentIdSet);
            return new ClientTO(version, id, firstName, lastName, phoneNumber, address, apartmentIdSet);
        }

        private void checkBeforeBuild(String firstName, String lastName, String phoneNumber, String address,
                                      List<Long> apartmentIdSet) {
            if (firstName == null || firstName.isEmpty() ||
                    lastName == null || lastName.isEmpty() ||
                    phoneNumber == null || phoneNumber.isEmpty() ||
                    address==null || address.isEmpty() ||
                    apartmentIdSet == null) {
                throw new RuntimeException("Incorrect client be created");
            }
        }
    }

    @Override
    public String toString() {
        return "ClientTO{" +
                "version=" + version +
                ", id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", address='" + address + '\'' +
                ", apartmentIdSet=" + apartmentIdSet +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClientTO clientTO = (ClientTO) o;
        return version == clientTO.version &&
                Objects.equals(id, clientTO.id) &&
                Objects.equals(firstName, clientTO.firstName) &&
                Objects.equals(lastName, clientTO.lastName) &&
                Objects.equals(phoneNumber, clientTO.phoneNumber) &&
                Objects.equals(address, clientTO.address) &&
                Objects.equals(apartmentIdSet, clientTO.apartmentIdSet);
    }

    @Override
    public int hashCode() {
        return Objects.hash(version, id, firstName, lastName, phoneNumber, address, apartmentIdSet);
    }
}
