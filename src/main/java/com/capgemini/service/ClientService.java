package com.capgemini.service;

import com.capgemini.exceptions.NoSuchApartmentException;
import com.capgemini.exceptions.NoSuchClientException;
import com.capgemini.exceptions.ReservationException;
import com.capgemini.types.ApartmentTO;
import com.capgemini.types.ClientTO;

import java.util.List;


public interface ClientService {

    ClientTO addNewClient(ClientTO clientTO);

    ClientTO updateClient(ClientTO clientTO);

    void deleteClient(ClientTO clientTO) throws NoSuchClientException;

    ClientTO findClientById(Long id) throws NoSuchClientException;

    List<ClientTO> findClientWithApartment(Long apartmentId);

    ClientTO buyApartment(ClientTO clientTO, ApartmentTO apartmentTO) throws NoSuchClientException, NoSuchApartmentException;

    ClientTO makeApartmentReservation(ClientTO clientTO, ApartmentTO apartmentTO) throws ReservationException, NoSuchApartmentException, NoSuchClientException;

    Double sumOfBoughtApartmentsPriceForSpecifiedClient(Long clientId) throws NoSuchClientException;

    List<ClientTO> clientsThatBoughtMoreThanOneApartment();
}
