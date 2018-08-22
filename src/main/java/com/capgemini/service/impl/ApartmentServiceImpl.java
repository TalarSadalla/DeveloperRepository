package com.capgemini.service.impl;

import com.capgemini.dao.ApartmentDao;
import com.capgemini.dao.BuildingDao;
import com.capgemini.domain.ApartmentEntity;
import com.capgemini.domain.BuildingEntity;
import com.capgemini.exceptions.NoSuchApartmentException;
import com.capgemini.exceptions.NoSuchBuildingException;
import com.capgemini.mappers.ApartmentMapper;
import com.capgemini.service.ApartmentService;
import com.capgemini.types.ApartmentTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ApartmentServiceImpl implements ApartmentService {

    @Autowired
    private ApartmentDao apartmentDao;

    @Autowired
    private BuildingDao buildingDao;

    @Override
    public ApartmentTO addNewApartment(ApartmentTO apartmentTO) {
        ApartmentEntity apartmentEntity = ApartmentMapper.toApartmentEntity(apartmentTO);
        Optional<BuildingEntity> optionalBuildingEntity = buildingDao.findById(apartmentTO.getBuildingId());
        if (optionalBuildingEntity.isPresent()) {
            optionalBuildingEntity.get().getListOfApartments().add(apartmentEntity);
            apartmentEntity.setBuildingEntity(optionalBuildingEntity.get());
        } else {
            throw new NoSuchBuildingException("No such building found");
        }
        apartmentEntity = apartmentDao.save(apartmentEntity);
        return ApartmentMapper.toApartmentTO(apartmentEntity);
    }

    @Override
    public ApartmentTO updateApartment(ApartmentTO apartmentTO) {
        ApartmentEntity apartmentEntity = ApartmentMapper.toApartmentEntity(apartmentTO);
        Optional<BuildingEntity> optionalBuildingEntity = buildingDao.findById(apartmentTO.getBuildingId());
        if (optionalBuildingEntity.isPresent()) {
            optionalBuildingEntity.get().getListOfApartments().add(apartmentEntity);
            apartmentEntity.setBuildingEntity(optionalBuildingEntity.get());
        } else {
            throw new NoSuchApartmentException("No such apartment found");
        }
        apartmentEntity = apartmentDao.save(apartmentEntity);
        return ApartmentMapper.toApartmentTO(apartmentEntity);
    }

    @Override
    public void deleteApartment(ApartmentTO apartmentTO) {
        apartmentDao.delete(ApartmentMapper.toApartmentEntity(apartmentTO));
        Optional<BuildingEntity> optionalBuildingEntity = buildingDao.findById(apartmentTO.getBuildingId());
        if (optionalBuildingEntity.isPresent()) {
            optionalBuildingEntity.get().setApartmentNo(optionalBuildingEntity.get().getApartmentNo() - 1);
            optionalBuildingEntity.get().getListOfApartments().remove(ApartmentMapper.toApartmentEntity(apartmentTO));
        } else {
            throw new NoSuchApartmentException("No such apartment found");
        }
    }

    @Override
    public ApartmentTO findApartmentById(Long id) {
        Optional<ApartmentEntity> optionalApartmentEntity = apartmentDao.findById(id);
        if (optionalApartmentEntity.isPresent()) return ApartmentMapper.toApartmentTO(optionalApartmentEntity.get());
        else throw new NoSuchApartmentException("No such apartment found");
    }

    @Override
    public ApartmentTO findApartmentByAddress(String address) {
        ApartmentEntity apartmentEntity = apartmentDao.findApartmentByAddress(address);
        if (apartmentEntity == null) throw new NoSuchApartmentException("No such apartment found");
        else return ApartmentMapper.toApartmentTO(apartmentEntity);
    }

    @Override
    public ApartmentTO findApartmentByApartmentSize(Double apartmentSize) {
        ApartmentEntity apartmentEntity = apartmentDao.findApartmentByApartmentSize(apartmentSize);
        if (apartmentEntity == null) throw new NoSuchApartmentException("No such apartment found");
        else return ApartmentMapper.toApartmentTO(apartmentEntity);
    }
}