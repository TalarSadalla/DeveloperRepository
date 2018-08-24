package com.capgemini.service;

import com.capgemini.types.ApartmentSearchCriteriaTO;
import com.capgemini.types.ApartmentTO;

import java.util.List;


public interface ApartmentService {

    ApartmentTO addNewApartment(ApartmentTO apartmentTO);

    ApartmentTO updateApartment(ApartmentTO apartmentTO);

    void deleteApartment(ApartmentTO apartmentTO);

    ApartmentTO findApartmentById(Long id);

    ApartmentTO findApartmentByAddress(String address);

    List<ApartmentTO> findApartmentsByCriteria(ApartmentSearchCriteriaTO apartmentSearchCriteriaTO);

}
