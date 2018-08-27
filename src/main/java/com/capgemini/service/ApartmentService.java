package com.capgemini.service;

import com.capgemini.exceptions.CriteriaSearchException;
import com.capgemini.exceptions.NoSuchApartmentException;
import com.capgemini.exceptions.NoSuchBuildingException;
import com.capgemini.types.ApartmentSearchCriteriaTO;
import com.capgemini.types.ApartmentTO;

import java.util.List;


public interface ApartmentService {

    ApartmentTO addNewApartment(ApartmentTO apartmentTO) throws NoSuchBuildingException;

    ApartmentTO updateApartment(ApartmentTO apartmentTO) throws NoSuchApartmentException;

    void deleteApartment(ApartmentTO apartmentTO) throws NoSuchApartmentException;

    ApartmentTO findApartmentById(Long id) throws NoSuchApartmentException;

    ApartmentTO findApartmentByAddress(String address) throws NoSuchApartmentException;

    List<ApartmentTO> findApartmentsByCriteria(ApartmentSearchCriteriaTO apartmentSearchCriteriaTO) throws CriteriaSearchException;

    List<ApartmentTO> findApartmentsForDisabledClients() throws NoSuchApartmentException;
}
