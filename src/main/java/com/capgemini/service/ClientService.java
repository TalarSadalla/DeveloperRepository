package com.capgemini.service;

import com.capgemini.types.ClientTO;


public interface ClientService {

    ClientTO addNewClient(ClientTO clientTO);

    ClientTO updateClient(ClientTO clientTO);

    void deleteClient(ClientTO clientTO);

    ClientTO findClientById(Long id);
}
