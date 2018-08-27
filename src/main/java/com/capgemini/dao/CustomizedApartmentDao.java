package com.capgemini.dao;

import com.capgemini.domain.ApartmentEntity;
import com.capgemini.exceptions.CriteriaSearchException;
import com.capgemini.types.ApartmentSearchCriteriaTO;

import java.util.List;

/**
 * Customized Apartment Dao
 */
public interface CustomizedApartmentDao {

    /**
     *
     * @param apartmentSearchCriteriaTO possible criteria of apartment search
     * @return List of Apartments that fulfill the search criteria
     * @throws CriteriaSearchException when there are no such Criteria
     */
    List<ApartmentEntity> findApartmentsByCriteria(ApartmentSearchCriteriaTO apartmentSearchCriteriaTO) throws CriteriaSearchException;

}
