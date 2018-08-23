package com.capgemini.service.impl;

import com.capgemini.dao.ApartmentDao;
import com.capgemini.dao.BuildingDao;
import com.capgemini.domain.ApartmentEntity;
import com.capgemini.domain.BuildingEntity;
import com.capgemini.exceptions.NoSuchBuildingException;
import com.capgemini.mappers.ApartmentMapper;
import com.capgemini.mappers.BuildingMapper;
import com.capgemini.service.BuildingService;
import com.capgemini.types.ApartmentTO;
import com.capgemini.types.BuildingTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class BuildingServiceImpl implements BuildingService {

    @Autowired
    private ApartmentDao apartmentDao;

    @Autowired
    private BuildingDao buildingDao;

    @Override
    public BuildingTO addNewBuilding(BuildingTO buildingTO) {
        BuildingEntity buildingEntity = buildingDao.save(BuildingMapper.toBuildingEntity(buildingTO));
        List<ApartmentEntity> listOfApartments = new ArrayList<>();
        buildingEntity.setListOfApartments(listOfApartments);
        return BuildingMapper.toBuildingTO(buildingEntity);
    }

    @Override
    public BuildingTO updateBuilding(BuildingTO buildingTO) {
        BuildingEntity buildingEntity = buildingDao.save(BuildingMapper.toBuildingEntity(buildingTO));
        List<Long> listOfApartmentsId = buildingTO.getListOfApartments();
        List<ApartmentEntity> apartmentsEntityList = new ArrayList<>();
        for (Long apartmentId : listOfApartmentsId) {
            apartmentsEntityList.add(apartmentDao.findById(apartmentId).get());
        }
        buildingEntity.setListOfApartments(apartmentsEntityList);
        return BuildingMapper.toBuildingTO(buildingEntity);
    }

    @Override
    public void deleteBuilding(BuildingTO buildingTO){
        Optional<BuildingEntity> optionalEntityToDelete = buildingDao.findById(buildingTO.getId());
        if (optionalEntityToDelete.isPresent()) buildingDao.delete(BuildingMapper.toBuildingEntity(buildingTO));
        else throw new NoSuchBuildingException("No such building with this id");
    }

    @Override
    public BuildingTO findBuildingById(Long id) {
        Optional<BuildingEntity> optionalBuildingEntity = buildingDao.findById(id);
        if (optionalBuildingEntity.isPresent()) {
            return BuildingMapper.toBuildingTO(optionalBuildingEntity.get());
        } else {
            throw new NoSuchBuildingException();
        }
    }


    @Override
    public BuildingTO findBuildingByLocalization(String localization) {
        if (buildingDao.findBuildingByLocalization(localization) == null) {
            throw new NoSuchBuildingException("No such building with this localization");
        } else return BuildingMapper.toBuildingTO(buildingDao.findBuildingByLocalization(localization));
    }

}
