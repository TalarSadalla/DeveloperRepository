package com.capgemini.service.impl;

import com.capgemini.dao.ClientDao;
import com.capgemini.domain.ApartmentEntity;
import com.capgemini.domain.ClientEntity;
import com.capgemini.exceptions.NoSuchClientException;
import com.capgemini.mappers.ClientMapper;
import com.capgemini.service.ClientService;
import com.capgemini.types.ClientTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class ClientServiceImpl implements ClientService {

    @Autowired
    ClientDao clientDao;

    @Override
    public ClientTO addNewClient(ClientTO clientTO) {
        ClientEntity clientEntity = clientDao.save(ClientMapper.toClientEntity(clientTO));
        Set<ApartmentEntity> listOfApartments = new HashSet<>();
        clientEntity.setApartmentEntitySet(listOfApartments);
        return ClientMapper.toClientTO(clientEntity);
    }

    @Override
    public ClientTO updateClient(ClientTO clientTO) {
        return null;
    }

    @Override
    public void deleteClient(ClientTO clientTO) {

    }

    @Override
    public ClientTO findClientById(Long id) {
        return null;
    }
}
