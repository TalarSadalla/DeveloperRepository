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

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
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
    public void deleteClient(ClientTO clientTO) throws NoSuchClientException {
        Optional<ClientEntity> optionalEntityToDelete = clientDao.findById(clientTO.getId());
        if (optionalEntityToDelete.isPresent()) clientDao.delete(ClientMapper.toClientEntity(clientTO));
        else throw new NoSuchClientException("No such client with this id");
    }

    @Override
    public ClientTO findClientById(Long id) throws NoSuchClientException {
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

    /**
     * Buy apartment by Client
     *
     * @param clientTO    Client that will buy apartment
     * @param apartmentTO apartment to be bought
     * @return Client that bought apartment
     * @throws NoSuchClientException    if Client does not exist
     * @throws NoSuchApartmentException if Apartment does not exist
     */
    @Override
    public ClientTO buyApartment(ClientTO clientTO, ApartmentTO apartmentTO) throws NoSuchClientException, NoSuchApartmentException {
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

    /**
     * Make apartment reservation by Client
     *
     * @param clientTO    Client that will buy apartment
     * @param apartmentTO apartment to be bought
     * @return Client that bought apartment
     * @throws ReservationException     if Client has more than 3 reservations
     * @throws NoSuchApartmentException if Apartment does not exist
     * @throws NoSuchClientException    if Client does not exist
     */
    @Override
    public ClientTO makeApartmentReservation(ClientTO clientTO, ApartmentTO apartmentTO) throws ReservationException, NoSuchApartmentException, NoSuchClientException {

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

    /**
     * Find sum of apartments price that are bought by specified Client
     * @param clientId client Id
     * @return Sum of apartments price
     */
    @Override
    public Double sumOfBoughtApartmentsPriceForSpecifiedClient(Long clientId) throws NoSuchClientException {
        ClientEntity clientEntity = clientDao.findById(clientId).orElseThrow(NoSuchClientException::new);
        Double apartmentsPrice = clientDao.sumOfBoughtApartmentsPriceForSpecifiedCLient(clientEntity.getId(), "BOUGHT");
        if (apartmentsPrice == null)
            return Double.valueOf(0);
        return apartmentsPrice;
    }

    /**
     * Find clients that bought more than one apartment
     * @return List of Clients that bought more than one apartment
     */
    @Override
    public List<ClientTO> clientsThatBoughtMoreThanOneApartment() {
        List<ClientEntity> clientEntities = clientDao.clientsThatBoughtMoreThanOneApartment();
        if (clientEntities.isEmpty() || clientEntities == null) return null;
        return ClientMapper.map2TOs(clientEntities);
    }

    /**
     * Get list of apartments owned by Client
     * @param clientTO client transfer object
     * @return List of Apartments owned by client
     */
    private List<ApartmentEntity> getListOfApartments(ClientTO clientTO) {
        List<Long> listOfApartmentsId = clientTO.getApartmentIdSet();
        List<ApartmentEntity> apartmentsEntityList = new ArrayList<>();
        for (Long apartmentId : listOfApartmentsId) {
            apartmentsEntityList.add(apartmentDao.findById(apartmentId).get());
        }
        return apartmentsEntityList;
    }

    /**
     * Validate if client can make a reservation
     * @param clientEntity
     * @throws ReservationException if Client has more thant 3 reservations
     */
    public void validateReservation(ClientEntity clientEntity) throws ReservationException {
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
