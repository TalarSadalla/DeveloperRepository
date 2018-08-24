package com.capgemini.service.impl;

import com.capgemini.dao.ApartmentDao;
import com.capgemini.dao.BuildingDao;
import com.capgemini.domain.ApartmentEntity;
import com.capgemini.domain.BuildingEntity;
import com.capgemini.exceptions.NoSuchApartmentException;
import com.capgemini.exceptions.NoSuchBuildingException;
import com.capgemini.mappers.BuildingMapper;
import com.capgemini.service.BuildingService;
import com.capgemini.types.BuildingTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
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
    public void deleteBuilding(BuildingTO buildingTO) throws NoSuchBuildingException {
        Optional<BuildingEntity> optionalEntityToDelete = buildingDao.findById(buildingTO.getId());
        if (optionalEntityToDelete.isPresent()) buildingDao.delete(BuildingMapper.toBuildingEntity(buildingTO));
        else throw new NoSuchBuildingException("No such building with this id");
    }

    @Override
    public BuildingTO findBuildingById(Long id) throws NoSuchBuildingException {
        Optional<BuildingEntity> optionalBuildingEntity = buildingDao.findById(id);
        if (optionalBuildingEntity.isPresent()) {
            return BuildingMapper.toBuildingTO(optionalBuildingEntity.get());
        } else {
            throw new NoSuchBuildingException();
        }
    }


    @Override
    public BuildingTO findBuildingByLocalization(String localization) throws NoSuchBuildingException {
        if (buildingDao.findBuildingByLocalization(localization) == null) {
            throw new NoSuchBuildingException("No such building with this localization");
        } else return BuildingMapper.toBuildingTO(buildingDao.findBuildingByLocalization(localization));
    }

    @Override
    public Double numberOfApartmentsInSpecifiedBuildingWithSpecifiedStatus(Long buildingId, String status) {
        Optional<BuildingEntity> optionalEntityToDelete = buildingDao.findById(buildingId);
        Double numberOfApartments;
        if (optionalEntityToDelete.isPresent()) {
            numberOfApartments = buildingDao.
                    numberOfApartmentsInSpecifiedBuildingWithSpecifiedStatus(buildingId, status);
            if (numberOfApartments == null || numberOfApartments == 0.0) {
                throw new NoSuchApartmentException("No apartments in this building with this status");
            }
        } else throw new NoSuchBuildingException("No such building with this id");
        return numberOfApartments;
    }
}
