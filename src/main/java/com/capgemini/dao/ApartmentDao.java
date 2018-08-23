package com.capgemini.dao;

import com.capgemini.domain.ApartmentEntity;
import com.capgemini.domain.BuildingEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface ApartmentDao extends CrudRepository<ApartmentEntity, Long> {

    ApartmentEntity findApartmentByApartmentSize(Double apartmentSize);

    ApartmentEntity findApartmentByRoomNo(Integer roomNo);

    ApartmentEntity findApartmentByBalconyNo(Integer balconyNo);

    ApartmentEntity findApartmentByFloor(Integer floor);

    ApartmentEntity findApartmentByAddress(String address);

    ApartmentEntity findApartmentByStatus(String status);

    ApartmentEntity findApartmentByApartmentPrice(Double apartmentPrice);

    List<ApartmentEntity> findByBuilding(BuildingEntity building);
}
