package com.capgemini.dao;

import com.capgemini.domain.ApartmentEntity;
import com.capgemini.domain.BuildingEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;

public interface BuildingDao extends CrudRepository<BuildingEntity, Long> {

    BuildingEntity findBuildingByDescription(String description);

    BuildingEntity findBuildingByLocalization(String localization);

    BuildingEntity findBuildingByFloorNo(Integer floorNo);

    BuildingEntity findBuildingByIsElevator(boolean isElevator);

    BuildingEntity findBuildingByApartmentNo(Integer apartmentNo);

    BuildingEntity findBuildingByListOfApartments(Set<ApartmentEntity> listOfApartments);

    @Query("select avg(loa.apartmentPrice) from BuildingEntity be " +
            "join be.listOfApartments loa on loa.building.id = :buildingId")
    Double averagePriceOfApartmentsInSpecifiedBuilding(@Param("buildingId") Long buildingId);

    @Query("select count(*) from ApartmentEntity ae " +
            "where ae.building.id = :buildingId and " +
            "ae.status=:status")
    Double numberOfApartmentsInSpecifiedBuildingWithSpecifiedStatus(@Param("buildingId") Long buildingId,
                                                                    @Param("status") String status);
}
