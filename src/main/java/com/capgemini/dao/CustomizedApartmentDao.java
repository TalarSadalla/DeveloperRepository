package com.capgemini.dao;

import com.capgemini.domain.ApartmentEntity;
import com.capgemini.types.ApartmentSearchCriteriaTO;

import java.util.List;

public interface CustomizedApartmentDao {

    List<ApartmentEntity> findApartmentsByCriteria(ApartmentSearchCriteriaTO apartmentSearchCriteriaTO);

}
