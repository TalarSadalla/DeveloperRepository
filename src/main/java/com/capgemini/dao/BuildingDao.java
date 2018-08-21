package com.capgemini.dao;

import com.capgemini.domain.ApartmentEntity;
import com.capgemini.domain.BuildingEntity;
import org.springframework.data.repository.CrudRepository;

import javax.persistence.Id;
import java.util.Set;

public interface BuildingDao extends CrudRepository<BuildingEntity, Long> {

    BuildingEntity findBuildingByDescription(String description);

    BuildingEntity findBuildingByLocalization(String localization);

    BuildingEntity findBuildingByFloorNo(int floorNo);

    BuildingEntity findBuildingByIsElevatior(boolean isElevator);

    BuildingEntity findBuildingByApartmentNo(int apartmentNo);

    BuildingEntity findBuildingByListOfApartments(Set<ApartmentEntity> listOfApartments);

}
