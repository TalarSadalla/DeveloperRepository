package com.capgemini.dao;

import com.capgemini.domain.ApartmentEntity;
import com.capgemini.domain.BuildingEntity;
import com.capgemini.domain.ClientEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

public interface ApartmentDao extends CrudRepository<ApartmentEntity, Long> {

    ApartmentEntity findApartmentByApartmentSize(Double apartmentSize);

    ApartmentEntity findApartmentByRoomNo(Integer roomNo);

    ApartmentEntity findApartmentByBalconyNo(Integer balconyNo);

    ApartmentEntity findApartmentByFloor(Integer floor);

    ApartmentEntity findApartmentByAddress(String address);

    ApartmentEntity findApartmentByStatus(String status);

    ApartmentEntity findApartmentByApartmentPrice(Double apartmentPrice);

    Set<ApartmentEntity> findByBuildingEntity(BuildingEntity buildingEntity);
}
