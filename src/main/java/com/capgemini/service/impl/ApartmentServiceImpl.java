package com.capgemini.service.impl;

import com.capgemini.dao.ApartmentDao;
import com.capgemini.dao.BuildingDao;
import com.capgemini.domain.ApartmentEntity;
import com.capgemini.domain.BuildingEntity;
import com.capgemini.exceptions.CriteriaSearchException;
import com.capgemini.exceptions.NoSuchApartmentException;
import com.capgemini.exceptions.NoSuchBuildingException;
import com.capgemini.mappers.ApartmentMapper;
import com.capgemini.service.ApartmentService;
import com.capgemini.types.ApartmentSearchCriteriaTO;
import com.capgemini.types.ApartmentTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ApartmentServiceImpl implements ApartmentService {

    @Autowired
    private ApartmentDao apartmentDao;

    @Autowired
    private BuildingDao buildingDao;

    @Override
    public ApartmentTO addNewApartment(ApartmentTO apartmentTO) throws NoSuchBuildingException {
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
    public ApartmentTO updateApartment(ApartmentTO apartmentTO) throws NoSuchApartmentException {
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
    public void deleteApartment(ApartmentTO apartmentTO) throws NoSuchApartmentException {
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
    public ApartmentTO findApartmentById(Long id) throws NoSuchApartmentException {
        Optional<ApartmentEntity> optionalApartmentEntity = apartmentDao.findById(id);
        if (optionalApartmentEntity.isPresent()) return ApartmentMapper.toApartmentTO(optionalApartmentEntity.get());
        else throw new NoSuchApartmentException("No such apartment found");
    }

    @Override
    public ApartmentTO findApartmentByAddress(String address) throws NoSuchApartmentException {
        ApartmentEntity apartmentEntity = apartmentDao.findApartmentByAddress(address);
        if (apartmentEntity == null) throw new NoSuchApartmentException("No such apartment found");
        else return ApartmentMapper.toApartmentTO(apartmentEntity);
    }

    /**
     * Find apartment by defined search criteria
     * @param apartmentSearchCriteriaTO
     * @return List of apartments that fulfill search criteria
     * @throws CriteriaSearchException if there are no search criteria defined
     */
    @Override
    public List<ApartmentTO> findApartmentsByCriteria(ApartmentSearchCriteriaTO apartmentSearchCriteriaTO) throws CriteriaSearchException {
        List<ApartmentEntity> apartmentEntities = new ArrayList<>();
        if (apartmentSearchCriteriaTO == null) return ApartmentMapper.map2TOs(apartmentEntities);
        apartmentEntities = apartmentDao.findApartmentsByCriteria(apartmentSearchCriteriaTO);
        return ApartmentMapper.map2TOs(apartmentEntities);
    }

    /**
     * Find list of apartments for disabled people
     * (building must have an elevator, or if it doesn't the apartment must be on floor 0)
     *
     * @return List of apartments that fulfill criteria
     * @throws NoSuchApartmentException if there are no available apartments for disabled Clients
     */
    @Override
    public List<ApartmentTO> findApartmentsForDisabledClients() throws NoSuchApartmentException {
        List<ApartmentEntity> apartmentEntityList = apartmentDao.findApartmentsForDisabledClients();
        if (apartmentEntityList.isEmpty() || apartmentEntityList == null) {
            throw new NoSuchApartmentException("No available apartments for disabled clients");
        }
        return ApartmentMapper.map2TOs(apartmentEntityList);
    }

}
