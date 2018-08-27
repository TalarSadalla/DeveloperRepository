package com.capgemini.dao;

import com.capgemini.domain.BuildingEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface BuildingDao extends CrudRepository<BuildingEntity, Long>, CustomizedBuildingDao {

    /**
     * Find building by specified localization
     * @param localization building localization
     * @return Building that fulfill localization criteria
     */
    BuildingEntity findBuildingByLocalization(String localization);

    /**
     * Find average price of apartment in specified building
     * @param buildingId building id
     * @return Average apartment price
     */
    @Query("select avg(loa.apartmentPrice) from BuildingEntity be " +
            "join be.listOfApartments loa on loa.building.id = :buildingId")
    Double averagePriceOfApartmentsInSpecifiedBuilding(@Param("buildingId") Long buildingId);

    /**
     * Find number of apartments in the building with specified status
     * @param buildingId building id
     * @param status apartment status that is located in the building
     * @return Number of apartments with specified status
     */
    @Query("select count(*) from ApartmentEntity ae " +
            "where ae.building.id = :buildingId and " +
            "ae.status=:status")
    Double numberOfApartmentsInSpecifiedBuildingWithSpecifiedStatus(@Param("buildingId") Long buildingId,
                                                                    @Param("status") String status);

}
