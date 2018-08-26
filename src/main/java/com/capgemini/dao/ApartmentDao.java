package com.capgemini.dao;

import com.capgemini.domain.ApartmentEntity;
import com.capgemini.domain.BuildingEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface ApartmentDao extends CrudRepository<ApartmentEntity, Long>, CustomizedApartmentDao {

    ApartmentEntity findApartmentByApartmentSize(Double apartmentSize);

    ApartmentEntity findApartmentByRoomNo(Integer roomNo);

    ApartmentEntity findApartmentByBalconyNo(Integer balconyNo);

    ApartmentEntity findApartmentByFloor(Integer floor);

    ApartmentEntity findApartmentByAddress(String address);

    ApartmentEntity findApartmentByStatus(String status);

    ApartmentEntity findApartmentByApartmentPrice(Double apartmentPrice);

    List<ApartmentEntity> findByBuilding(BuildingEntity building);

    @Query( "select ae from ApartmentEntity ae join ae.building " +
            "where ae.building.isElevator = 'true' or ae.floor = 0")
    List<ApartmentEntity> findApartmentsForDisabledClients();
}
