package com.capgemini.dao;

import com.capgemini.domain.ApartmentEntity;
import com.capgemini.domain.BuildingEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;


public interface BuildingDao extends CrudRepository<BuildingEntity, Long> {

    BuildingEntity findBuildingByDescription(String description);

    BuildingEntity findBuildingByLocalization(String localization);

    BuildingEntity findBuildingByFloorNo(Integer floorNo);

    BuildingEntity findBuildingByIsElevator(boolean isElevator);

    BuildingEntity findBuildingByApartmentNo(Integer apartmentNo);

    BuildingEntity findBuildingByListOfApartments(Set<ApartmentEntity> listOfApartments);

}
