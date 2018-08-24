package com.capgemini.service;

import com.capgemini.types.ApartmentTO;
import com.capgemini.types.ClientTO;

import java.util.List;


public interface ClientService {

    ClientTO addNewClient(ClientTO clientTO);

    ClientTO updateClient(ClientTO clientTO);

    void deleteClient(ClientTO clientTO);

    ClientTO findClientById(Long id);

    List<ClientTO> findClientWithApartment(Long apartmentId);

    ClientTO buyApartment(ClientTO clientTO, ApartmentTO apartmentTO);

    ClientTO makeApartmentReservation(ClientTO clientTO, ApartmentTO apartmentTO);

    Double sumOfBoughtApartmentsPriceForSpecifiedClient(Long clientId);

    List<ClientTO> clientsThatBoughtMoreThanOneApartment();
}
