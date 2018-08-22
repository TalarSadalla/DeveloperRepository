package com.capgemini.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;


import com.capgemini.types.ApartmentTO;
import com.capgemini.types.ApartmentTO.ApartmentTOBuilder;
import com.capgemini.types.BuildingTO;
import com.capgemini.types.BuildingTO.BuildingTOBuilder;
import com.capgemini.types.ClientTO;
import com.capgemini.types.ClientTO.ClientTOBuilder;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.Temporal;
import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Set;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = "spring.profiles.active=hsql")
public class ClientServiceTest {

    @Autowired
    private ApartmentService apartmentService;

    @Autowired
    private ClientService clientService;

    @Autowired
    private BuildingService buildingService;

    @Test
    @Transactional
    public void shouldAddClient(){

        //given
        ApartmentTO apartmentTO=new ApartmentTOBuilder()
                .withAddress("Murawa")
                .withStatus("FREE")
                .withRoomNo(10)
                .withFloor(2)
                .withBalconyNo(2)
                .withBuildingId((long) 1)
                .withApartmentSize(75.19)
                .withApartmentPrice(440000.00)
                .build();
        Set<Long> apartmentTOSet= new HashSet<>();

        BuildingTO buildingTO = new BuildingTOBuilder()
                .withLocalization("Poznan")
                .withApartmentNumber(32)
                .withDescription("Nowy Marcelin")
                .withElevator(true)
                .withFloorNumber(5)
                .withListOfApartments(apartmentTOSet)
                .build();

        ClientTO clientTO=new ClientTO.ClientTOBuilder()
                .withFirstName("Talar")
                .withLastName("Sadalla")
                .withAddress("Ptasia")
                .withPhoneNumber("643 454 321")
                .withApartmentIds(apartmentTOSet)
                .build();

        //when

        ClientTO savedClient=clientService.addNewClient(clientTO);

        //then
        assertEquals("Talar",savedClient.getFirstName());
        assertEquals("Sadalla",savedClient.getLastName());

    }

    @Test
    @Transactional
    public void shouldUpdateClient(){

        //given
        ApartmentTO apartmentTO=new ApartmentTOBuilder()
                .withAddress("Murawa")
                .withStatus("FREE")
                .withRoomNo(10)
                .withFloor(2)
                .withBalconyNo(2)
                .withBuildingId((long) 1)
                .withApartmentSize(75.19)
                .withApartmentPrice(440000.00)
                .build();
        Set<Long> apartmentTOSet= new HashSet<>();

        BuildingTO buildingTO = new BuildingTOBuilder()
                .withLocalization("Poznan")
                .withApartmentNumber(32)
                .withDescription("Nowy Marcelin")
                .withElevator(true)
                .withFloorNumber(5)
                .withListOfApartments(apartmentTOSet)
                .build();

        ClientTO clientTO=new ClientTO.ClientTOBuilder()
                .withFirstName("Talar")
                .withLastName("Sadalla")
                .withAddress("Ptasia")
                .withPhoneNumber("643 454 321")
                .withApartmentIds(apartmentTOSet)
                .build();

        //when

        ClientTO savedClient=clientService.addNewClient(clientTO);

        //then
        assertEquals("Talar",savedClient.getFirstName());
        assertEquals("Sadalla",savedClient.getLastName());

    }
}
