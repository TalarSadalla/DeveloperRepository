package com.capgemini.service;

import com.capgemini.types.ApartmentTO;


public interface ApartmentService {

        ApartmentTO addNewApartment(ApartmentTO apartmentTO) ;

        ApartmentTO updateApartment(ApartmentTO apartmentTO);

        void deleteApartment(ApartmentTO apartmentTO) ;

    ApartmentTO findApartmentById(Long id);

    ApartmentTO findApartmentByAddress(String address);

    ApartmentTO findApartmentByApartmentSize(Double apartmentSize) ;

}
