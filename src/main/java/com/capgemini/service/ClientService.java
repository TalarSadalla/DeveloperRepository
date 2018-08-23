package com.capgemini.service;

import com.capgemini.types.ApartmentTO;
import com.capgemini.types.ClientTO;

import java.util.List;
import java.util.Set;


public interface ClientService {

    ClientTO addNewClient(ClientTO clientTO);

    ClientTO updateClient(ClientTO clientTO);

    void deleteClient(ClientTO clientTO);

    ClientTO findClientById(Long id);

    List<ClientTO> findClientWithApartment(Long apartmentId);

    ClientTO buyApartment(ClientTO clientTO, ApartmentTO apartmentTO);

    ClientTO makeApartmentReservation(ClientTO clientTO, ApartmentTO apartmentTO);
}
