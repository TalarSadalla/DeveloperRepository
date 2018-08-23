package com.capgemini.service.impl;

import com.capgemini.dao.ApartmentDao;
import com.capgemini.dao.ClientDao;
import com.capgemini.domain.ApartmentEntity;
import com.capgemini.domain.ClientEntity;
import com.capgemini.exceptions.NoSuchApartmentException;
import com.capgemini.exceptions.NoSuchClientException;
import com.capgemini.exceptions.ReservationException;
import com.capgemini.mappers.ClientMapper;
import com.capgemini.service.ClientService;
import com.capgemini.types.ApartmentTO;
import com.capgemini.types.ClientTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClientServiceImpl implements ClientService {

    @Autowired
    ClientDao clientDao;

    @Autowired
    ApartmentDao apartmentDao;

    @Override
    public ClientTO addNewClient(ClientTO clientTO) {
        ClientEntity clientEntity = clientDao.save(ClientMapper.toClientEntity(clientTO));
        List<ApartmentEntity> listOfApartments = new ArrayList<>();
        clientEntity.setApartmentEntitySet(listOfApartments);
        return ClientMapper.toClientTO(clientEntity);
    }

    @Override
    public ClientTO updateClient(ClientTO clientTO) {
        ClientEntity clientEntity = clientDao.save(ClientMapper.toClientEntity(clientTO));
        List<ApartmentEntity> apartmentsEntityList = getListOfApartments(clientTO);
        clientEntity.setApartmentEntitySet(apartmentsEntityList);
        return ClientMapper.toClientTO(clientEntity);
    }

    @Override
    public void deleteClient(ClientTO clientTO) {
        Optional<ClientEntity> optionalEntityToDelete = clientDao.findById(clientTO.getId());
        if (optionalEntityToDelete.isPresent()) clientDao.delete(ClientMapper.toClientEntity(clientTO));
        else throw new NoSuchClientException("No such client with this id");
    }

    @Override
    public ClientTO findClientById(Long id) {
        Optional<ClientEntity> optionalClientEntity = clientDao.findById(id);
        if (optionalClientEntity.isPresent()) {
            return ClientMapper.toClientTO(optionalClientEntity.get());
        } else {
            throw new NoSuchClientException();
        }
    }

    @Override
    public List<ClientTO> findClientWithApartment(Long apartmentId) {
        List<ClientEntity> listOFClients = clientDao.findClientWithApartment(apartmentId);
        return ClientMapper.map2TOs(listOFClients);
    }

    @Override
    public ClientTO buyApartment(ClientTO clientTO, ApartmentTO apartmentTO) {
        ApartmentEntity apartmentEntity = apartmentDao.findById(apartmentTO.getId())
                .orElseThrow(NoSuchApartmentException::new);
        ClientEntity clientEntity = clientDao.findById(clientTO.getId())
                .orElseThrow(NoSuchClientException::new);

        apartmentEntity.setStatus("BOUGHT");
        apartmentEntity = apartmentDao.save(apartmentEntity);

        List<ApartmentEntity> apartmentsEntityList = getListOfApartments(clientTO);
        apartmentsEntityList.add(apartmentEntity);

        clientEntity.setApartmentEntitySet(apartmentsEntityList);
        clientEntity = clientDao.save(clientEntity);

        return ClientMapper.toClientTO(clientEntity);
    }

    @Override
    public ClientTO makeApartmentReservation(ClientTO clientTO, ApartmentTO apartmentTO) {

        ApartmentEntity apartmentEntity = apartmentDao.findById(apartmentTO.getId())
                .orElseThrow(NoSuchApartmentException::new);
        ClientEntity clientEntity = clientDao.findById(clientTO.getId())
                .orElseThrow(NoSuchClientException::new);

        validateReservation(clientEntity);

        apartmentEntity.setStatus("RESERVATION");
        apartmentEntity = apartmentDao.save(apartmentEntity);

        List<ApartmentEntity> apartmentsEntityList = getListOfApartments(clientTO);
        apartmentsEntityList.add(apartmentEntity);

        clientEntity.setApartmentEntitySet(apartmentsEntityList);
        clientEntity = clientDao.save(clientEntity);

        return ClientMapper.toClientTO(clientEntity);
    }

    private List<ApartmentEntity> getListOfApartments(ClientTO clientTO) {
        List<Long> listOfApartmentsId = clientTO.getApartmentIdSet();
        List<ApartmentEntity> apartmentsEntityList = new ArrayList<>();
        for (Long apartmentId : listOfApartmentsId) {
            apartmentsEntityList.add(apartmentDao.findById(apartmentId).get());
        }
        return apartmentsEntityList;
    }

    public void validateReservation(ClientEntity clientEntity) {
        List<ApartmentEntity> apartmentEntityList = clientEntity.getApartmentEntitySet()
                .stream().filter(apartment -> apartment.getStatus().toUpperCase().equals("RESERVATION"))
                .collect(Collectors.toList());

        List<ApartmentEntity> apartmentsWithOneOwner = new ArrayList<>();

        for (ApartmentEntity apartment : apartmentEntityList) {
            if (clientDao.findClientWithApartment(apartment.getId()).size() < 2) {
                apartmentsWithOneOwner.add(apartment);
            }
        }
        if (apartmentsWithOneOwner.size() >= 3) {
            throw new ReservationException("To many reservations");
        }
    }
}
