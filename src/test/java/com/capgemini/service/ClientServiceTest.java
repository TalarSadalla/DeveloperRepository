package com.capgemini.service;


import com.capgemini.exceptions.NoSuchClientException;
import com.capgemini.exceptions.ReservationException;
import com.capgemini.types.ApartmentTO;
import com.capgemini.types.ApartmentTO.ApartmentTOBuilder;
import com.capgemini.types.BuildingTO;
import com.capgemini.types.BuildingTO.BuildingTOBuilder;
import com.capgemini.types.ClientTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;

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
    public void shouldAddClient() {

        //given
        ApartmentTO apartmentTO = new ApartmentTOBuilder()
                .withAddress("Murawa")
                .withStatus("FREE")
                .withRoomNo(10)
                .withFloor(2)
                .withBalconyNo(2)
                .withBuildingId((long) 1)
                .withApartmentSize(75.19)
                .withApartmentPrice(440000.00)
                .build();
        List<Long> apartmentTOSet = new ArrayList<>();

        BuildingTO buildingTO = new BuildingTOBuilder()
                .withLocalization("Poznan")
                .withApartmentNumber(32)
                .withDescription("Nowy Marcelin")
                .withElevator(true)
                .withFloorNumber(5)
                .withListOfApartments(apartmentTOSet)
                .build();

        ClientTO clientTO = new ClientTO.ClientTOBuilder()
                .withFirstName("Talar")
                .withLastName("Sadalla")
                .withAddress("Ptasia")
                .withPhoneNumber("643 454 321")
                .withApartmentIds(apartmentTOSet)
                .build();

        //when

        ClientTO savedClient = clientService.addNewClient(clientTO);

        //then
        assertEquals("Talar", savedClient.getFirstName());
        assertEquals("Sadalla", savedClient.getLastName());

    }

    @Test
    @Transactional
    public void shouldUpdateClient() {

        //given
        ApartmentTO apartmentTO = new ApartmentTOBuilder()
                .withAddress("Murawa")
                .withStatus("FREE")
                .withRoomNo(10)
                .withFloor(2)
                .withBalconyNo(2)
                .withBuildingId((long) 1)
                .withApartmentSize(75.19)
                .withApartmentPrice(440000.00)
                .build();
        List<Long> apartmentTOSet = new ArrayList<>();

        BuildingTO buildingTO = new BuildingTOBuilder()
                .withLocalization("Poznan")
                .withApartmentNumber(32)
                .withDescription("Nowy Marcelin")
                .withElevator(true)
                .withFloorNumber(5)
                .withListOfApartments(apartmentTOSet)
                .build();

        ClientTO clientTO = new ClientTO.ClientTOBuilder()
                .withFirstName("Talar")
                .withLastName("Sadalla")
                .withAddress("Ptasia")
                .withPhoneNumber("643 454 321")
                .withApartmentIds(apartmentTOSet)
                .build();

        //when

        ClientTO savedClient = clientService.addNewClient(clientTO);
        savedClient.setAddress("Kraków");
        savedClient = clientService.updateClient(savedClient);

        //then
        assertEquals("Talar", savedClient.getFirstName());
        assertEquals("Sadalla", savedClient.getLastName());
        assertEquals("Kraków", savedClient.getAddress());

    }

    @Test(expected = NoSuchClientException.class)
    @Transactional
    public void shouldDeleteClient() {

        //given
        ApartmentTO apartmentTO = new ApartmentTOBuilder()
                .withAddress("Murawa")
                .withStatus("FREE")
                .withRoomNo(10)
                .withFloor(2)
                .withBalconyNo(2)
                .withBuildingId((long) 1)
                .withApartmentSize(75.19)
                .withApartmentPrice(440000.00)
                .build();
        List<Long> apartmentTOSet = new ArrayList<>();

        BuildingTO buildingTO = new BuildingTOBuilder()
                .withLocalization("Poznan")
                .withApartmentNumber(32)
                .withDescription("Nowy Marcelin")
                .withElevator(true)
                .withFloorNumber(5)
                .withListOfApartments(apartmentTOSet)
                .build();

        ClientTO clientTO = new ClientTO.ClientTOBuilder()
                .withFirstName("Talar")
                .withLastName("Sadalla")
                .withAddress("Ptasia")
                .withPhoneNumber("643 454 321")
                .withApartmentIds(apartmentTOSet)
                .build();

        //when
        ClientTO savedClient = clientService.addNewClient(clientTO);
        clientService.deleteClient(savedClient);
        ClientTO foundClient = clientService.findClientById(savedClient.getId());
    }

    @Test
    @Transactional
    public void shouldFindClientById() {

        //given
        ApartmentTO apartmentTO = new ApartmentTOBuilder()
                .withAddress("Murawa")
                .withStatus("FREE")
                .withRoomNo(10)
                .withFloor(2)
                .withBalconyNo(2)
                .withBuildingId((long) 1)
                .withApartmentSize(75.19)
                .withApartmentPrice(440000.00)
                .build();
        List<Long> apartmentTOSet = new ArrayList<>();

        BuildingTO buildingTO = new BuildingTOBuilder()
                .withLocalization("Poznan")
                .withApartmentNumber(32)
                .withDescription("Nowy Marcelin")
                .withElevator(true)
                .withFloorNumber(5)
                .withListOfApartments(apartmentTOSet)
                .build();

        ClientTO clientTO = new ClientTO.ClientTOBuilder()
                .withFirstName("Talar")
                .withLastName("Sadalla")
                .withAddress("Ptasia")
                .withPhoneNumber("643 454 321")
                .withApartmentIds(apartmentTOSet)
                .build();

        ClientTO clientTO2 = new ClientTO.ClientTOBuilder()
                .withFirstName("Marcin")
                .withLastName("Sadalla")
                .withAddress("Wrocław")
                .withPhoneNumber("643 454 321")
                .withApartmentIds(apartmentTOSet)
                .build();

        //when
        ClientTO savedClient = clientService.addNewClient(clientTO);
        ClientTO savedClient2 = clientService.addNewClient(clientTO2);
        ClientTO foundClient = clientService.findClientById(savedClient.getId());
        ClientTO foundClient2 = clientService.findClientById(savedClient2.getId());
        //then
        assertEquals(savedClient.getId(), foundClient.getId());
        assertEquals("Talar", foundClient.getFirstName());
        assertEquals(savedClient2.getId(), foundClient2.getId());
        assertEquals("Marcin", foundClient2.getFirstName());
    }

    @Test
    @Transactional
    public void shouldBuyApartmentByClient() {

        //given
        List<Long> apartmentTOSet = new ArrayList<>();

        BuildingTO buildingTO = new BuildingTOBuilder()
                .withLocalization("Poznan")
                .withApartmentNumber(32)
                .withDescription("Nowy Marcelin")
                .withElevator(true)
                .withFloorNumber(5)
                .withListOfApartments(apartmentTOSet)
                .build();

        BuildingTO foundBuilding = buildingService.addNewBuilding(buildingTO);

        ApartmentTO apartmentTO1 = new ApartmentTOBuilder()
                .withAddress("Murawa")
                .withStatus("FREE")
                .withRoomNo(2)
                .withFloor(2)
                .withBalconyNo(2)
                .withBuildingId(foundBuilding.getId())
                .withApartmentSize(75.19)
                .withApartmentPrice(440000.00)
                .build();

        ApartmentTO apartmentTO2 = new ApartmentTOBuilder()
                .withAddress("Murawa")
                .withStatus("FREE")
                .withRoomNo(3)
                .withFloor(0)
                .withBalconyNo(0)
                .withBuildingId(foundBuilding.getId())
                .withApartmentSize(67.89)
                .withApartmentPrice(385000.00)
                .build();

        ApartmentTO apartmentTO3 = new ApartmentTOBuilder()
                .withAddress("Murawa")
                .withStatus("RESERVED")
                .withRoomNo(5)
                .withFloor(5)
                .withBalconyNo(1)
                .withBuildingId(foundBuilding.getId())
                .withApartmentSize(92.19)
                .withApartmentPrice(687000.00)
                .build();

        ApartmentTO apartmentTO4 = new ApartmentTOBuilder()
                .withAddress("Murawa")
                .withStatus("RESERVED")
                .withRoomNo(1)
                .withFloor(0)
                .withBalconyNo(2)
                .withBuildingId(foundBuilding.getId())
                .withApartmentSize(32.81)
                .withApartmentPrice(227000.00)
                .build();

        ApartmentTO apartmentTO5 = new ApartmentTOBuilder()
                .withAddress("Murawa")
                .withStatus("BOUGHT")
                .withRoomNo(3)
                .withFloor(4)
                .withBalconyNo(1)
                .withBuildingId(foundBuilding.getId())
                .withApartmentSize(54.19)
                .withApartmentPrice(379000.00)
                .build();

        ApartmentTO apartmentTO6 = new ApartmentTOBuilder()
                .withAddress("Murawa")
                .withStatus("BOUGHT")
                .withRoomNo(2)
                .withFloor(3)
                .withBalconyNo(1)
                .withBuildingId(foundBuilding.getId())
                .withApartmentSize(48.94)
                .withApartmentPrice(440000.00)
                .build();

        ClientTO clientTO1 = new ClientTO.ClientTOBuilder()
                .withFirstName("Talar")
                .withLastName("Sadalla")
                .withAddress("Ptasia")
                .withPhoneNumber("643 454 321")
                .withApartmentIds(apartmentTOSet)
                .build();

        ClientTO clientTO2 = new ClientTO.ClientTOBuilder()
                .withFirstName("Marcin")
                .withLastName("Janusz")
                .withAddress("Ptasia")
                .withPhoneNumber("643 454 321")
                .withApartmentIds(apartmentTOSet)
                .build();

        //when

        ApartmentTO foundApartment1 = apartmentService.addNewApartment(apartmentTO1);
        ApartmentTO foundApartment2 = apartmentService.addNewApartment(apartmentTO2);
        ApartmentTO foundApartment3 = apartmentService.addNewApartment(apartmentTO3);
        ApartmentTO foundApartment4 = apartmentService.addNewApartment(apartmentTO4);
        ApartmentTO foundApartment5 = apartmentService.addNewApartment(apartmentTO5);
        ApartmentTO foundApartment6 = apartmentService.addNewApartment(apartmentTO6);

        apartmentTOSet.add(foundApartment1.getId());
        apartmentTOSet.add(foundApartment2.getId());
        apartmentTOSet.add(foundApartment3.getId());
        apartmentTOSet.add(foundApartment4.getId());
        //apartmentTOSet.add(foundApartment5.getId());
        //apartmentTOSet.add(foundApartment6.getId());

        foundBuilding.setListOfApartments(apartmentTOSet);
        foundBuilding = buildingService.updateBuilding(foundBuilding);

        ClientTO savedClient1 = clientService.addNewClient(clientTO1);
        ClientTO savedClient2 = clientService.addNewClient(clientTO2);

        savedClient1.setApartmentIdSet(apartmentTOSet);
        savedClient1 = clientService.updateClient(savedClient1);

        savedClient1 = clientService.buyApartment(savedClient1, foundApartment5);

        savedClient2.setApartmentIdSet(apartmentTOSet);
        savedClient2 = clientService.updateClient(savedClient2);


        //then
        assertEquals(5, savedClient1.getApartmentIdSet().size());

    }

    @Test
    @Transactional
    public void shouldMakeReservationApartmentByClient() {

        //given
        List<Long> apartmentTOSet = new ArrayList<>();

        BuildingTO buildingTO = new BuildingTOBuilder()
                .withLocalization("Poznan")
                .withApartmentNumber(32)
                .withDescription("Nowy Marcelin")
                .withElevator(true)
                .withFloorNumber(5)
                .withListOfApartments(apartmentTOSet)
                .build();

        BuildingTO foundBuilding = buildingService.addNewBuilding(buildingTO);

        ApartmentTO apartmentTO1 = new ApartmentTOBuilder()
                .withAddress("Murawa")
                .withStatus("FREE")
                .withRoomNo(2)
                .withFloor(2)
                .withBalconyNo(2)
                .withBuildingId(foundBuilding.getId())
                .withApartmentSize(75.19)
                .withApartmentPrice(440000.00)
                .build();

        ApartmentTO apartmentTO2 = new ApartmentTOBuilder()
                .withAddress("Murawa")
                .withStatus("FREE")
                .withRoomNo(3)
                .withFloor(0)
                .withBalconyNo(0)
                .withBuildingId(foundBuilding.getId())
                .withApartmentSize(67.89)
                .withApartmentPrice(385000.00)
                .build();

        ApartmentTO apartmentTO3 = new ApartmentTOBuilder()
                .withAddress("Murawa")
                .withStatus("RESERVED")
                .withRoomNo(5)
                .withFloor(5)
                .withBalconyNo(1)
                .withBuildingId(foundBuilding.getId())
                .withApartmentSize(92.19)
                .withApartmentPrice(687000.00)
                .build();

        ApartmentTO apartmentTO4 = new ApartmentTOBuilder()
                .withAddress("Murawa")
                .withStatus("RESERVED")
                .withRoomNo(1)
                .withFloor(0)
                .withBalconyNo(2)
                .withBuildingId(foundBuilding.getId())
                .withApartmentSize(32.81)
                .withApartmentPrice(227000.00)
                .build();

        ApartmentTO apartmentTO5 = new ApartmentTOBuilder()
                .withAddress("Murawa")
                .withStatus("BOUGHT")
                .withRoomNo(3)
                .withFloor(4)
                .withBalconyNo(1)
                .withBuildingId(foundBuilding.getId())
                .withApartmentSize(54.19)
                .withApartmentPrice(379000.00)
                .build();

        ApartmentTO apartmentTO6 = new ApartmentTOBuilder()
                .withAddress("Murawa")
                .withStatus("BOUGHT")
                .withRoomNo(2)
                .withFloor(3)
                .withBalconyNo(1)
                .withBuildingId(foundBuilding.getId())
                .withApartmentSize(48.94)
                .withApartmentPrice(440000.00)
                .build();

        ClientTO clientTO1 = new ClientTO.ClientTOBuilder()
                .withFirstName("Talar")
                .withLastName("Sadalla")
                .withAddress("Ptasia")
                .withPhoneNumber("643 454 321")
                .withApartmentIds(apartmentTOSet)
                .build();

        ClientTO clientTO2 = new ClientTO.ClientTOBuilder()
                .withFirstName("Marcin")
                .withLastName("Janusz")
                .withAddress("Ptasia")
                .withPhoneNumber("643 454 321")
                .withApartmentIds(apartmentTOSet)
                .build();

        //when

        ApartmentTO foundApartment1 = apartmentService.addNewApartment(apartmentTO1);
        ApartmentTO foundApartment2 = apartmentService.addNewApartment(apartmentTO2);
        ApartmentTO foundApartment3 = apartmentService.addNewApartment(apartmentTO3);
        ApartmentTO foundApartment4 = apartmentService.addNewApartment(apartmentTO4);
        ApartmentTO foundApartment5 = apartmentService.addNewApartment(apartmentTO5);
        ApartmentTO foundApartment6 = apartmentService.addNewApartment(apartmentTO6);

        apartmentTOSet.add(foundApartment1.getId());
        apartmentTOSet.add(foundApartment2.getId());
        apartmentTOSet.add(foundApartment3.getId());
        apartmentTOSet.add(foundApartment4.getId());
        //apartmentTOSet.add(foundApartment5.getId());
        //apartmentTOSet.add(foundApartment6.getId());

        foundBuilding.setListOfApartments(apartmentTOSet);
        foundBuilding = buildingService.updateBuilding(foundBuilding);

        ClientTO savedClient1 = clientService.addNewClient(clientTO1);
        ClientTO savedClient2 = clientService.addNewClient(clientTO2);

        savedClient1.setApartmentIdSet(apartmentTOSet);
        savedClient1 = clientService.updateClient(savedClient1);

        savedClient1 = clientService.makeApartmentReservation(savedClient1, foundApartment5);

        savedClient2.setApartmentIdSet(apartmentTOSet);
        savedClient2 = clientService.updateClient(savedClient2);


        //then
        assertEquals(5, savedClient1.getApartmentIdSet().size());
    }

    @Test(expected = ReservationException.class)
    @Transactional
    public void shouldMakeReservationApartmentByClientException() {

        //given
        List<Long> apartmentTOSet = new ArrayList<>();

        BuildingTO buildingTO = new BuildingTOBuilder()
                .withLocalization("Poznan")
                .withApartmentNumber(32)
                .withDescription("Nowy Marcelin")
                .withElevator(true)
                .withFloorNumber(5)
                .withListOfApartments(apartmentTOSet)
                .build();

        BuildingTO foundBuilding = buildingService.addNewBuilding(buildingTO);

        ApartmentTO apartmentTO1 = new ApartmentTOBuilder()
                .withAddress("Murawa")
                .withStatus("FREE")
                .withRoomNo(2)
                .withFloor(2)
                .withBalconyNo(2)
                .withBuildingId(foundBuilding.getId())
                .withApartmentSize(75.19)
                .withApartmentPrice(440000.00)
                .build();

        ApartmentTO apartmentTO2 = new ApartmentTOBuilder()
                .withAddress("Murawa")
                .withStatus("RESERVATION")
                .withRoomNo(3)
                .withFloor(0)
                .withBalconyNo(0)
                .withBuildingId(foundBuilding.getId())
                .withApartmentSize(67.89)
                .withApartmentPrice(385000.00)
                .build();

        ApartmentTO apartmentTO3 = new ApartmentTOBuilder()
                .withAddress("Murawa")
                .withStatus("RESERVATION")
                .withRoomNo(5)
                .withFloor(5)
                .withBalconyNo(1)
                .withBuildingId(foundBuilding.getId())
                .withApartmentSize(92.19)
                .withApartmentPrice(687000.00)
                .build();

        ApartmentTO apartmentTO4 = new ApartmentTOBuilder()
                .withAddress("Murawa")
                .withStatus("RESERVATION")
                .withRoomNo(1)
                .withFloor(0)
                .withBalconyNo(2)
                .withBuildingId(foundBuilding.getId())
                .withApartmentSize(32.81)
                .withApartmentPrice(227000.00)
                .build();

        ApartmentTO apartmentTO5 = new ApartmentTOBuilder()
                .withAddress("Murawa")
                .withStatus("BOUGHT")
                .withRoomNo(3)
                .withFloor(4)
                .withBalconyNo(1)
                .withBuildingId(foundBuilding.getId())
                .withApartmentSize(54.19)
                .withApartmentPrice(379000.00)
                .build();

        ApartmentTO apartmentTO6 = new ApartmentTOBuilder()
                .withAddress("Murawa")
                .withStatus("BOUGHT")
                .withRoomNo(2)
                .withFloor(3)
                .withBalconyNo(1)
                .withBuildingId(foundBuilding.getId())
                .withApartmentSize(48.94)
                .withApartmentPrice(440000.00)
                .build();

        ClientTO clientTO1 = new ClientTO.ClientTOBuilder()
                .withFirstName("Talar")
                .withLastName("Sadalla")
                .withAddress("Ptasia")
                .withPhoneNumber("643 454 321")
                .withApartmentIds(apartmentTOSet)
                .build();

        ClientTO clientTO2 = new ClientTO.ClientTOBuilder()
                .withFirstName("Marcin")
                .withLastName("Janusz")
                .withAddress("Ptasia")
                .withPhoneNumber("643 454 321")
                .withApartmentIds(apartmentTOSet)
                .build();

        //when

        ApartmentTO foundApartment1 = apartmentService.addNewApartment(apartmentTO1);
        ApartmentTO foundApartment2 = apartmentService.addNewApartment(apartmentTO2);
        ApartmentTO foundApartment3 = apartmentService.addNewApartment(apartmentTO3);
        ApartmentTO foundApartment4 = apartmentService.addNewApartment(apartmentTO4);
        ApartmentTO foundApartment5 = apartmentService.addNewApartment(apartmentTO5);
        ApartmentTO foundApartment6 = apartmentService.addNewApartment(apartmentTO6);

        apartmentTOSet.add(foundApartment1.getId());
        apartmentTOSet.add(foundApartment2.getId());
        apartmentTOSet.add(foundApartment3.getId());
        apartmentTOSet.add(foundApartment4.getId());


        ClientTO savedClient1 = clientService.addNewClient(clientTO1);
        ClientTO savedClient2 = clientService.addNewClient(clientTO2);

        savedClient1.setApartmentIdSet(apartmentTOSet);
        savedClient1 = clientService.updateClient(savedClient1);

        apartmentTOSet.add(foundApartment5.getId());
        apartmentTOSet.add(foundApartment6.getId());
        foundBuilding.setListOfApartments(apartmentTOSet);
        foundBuilding = buildingService.updateBuilding(foundBuilding);

        savedClient1 = clientService.makeApartmentReservation(savedClient1, foundApartment5);

        savedClient2.setApartmentIdSet(apartmentTOSet);
        savedClient2 = clientService.updateClient(savedClient2);
        //
        //
        //        //then
        assertEquals(5, savedClient1.getApartmentIdSet().size());
    }

    @Test
    @Transactional
    public void shouldMakeReservationApartmentByMultipleClients() {

        //given
        List<Long> apartmentTOSet = new ArrayList<>();

        BuildingTO buildingTO = new BuildingTOBuilder()
                .withLocalization("Poznan")
                .withApartmentNumber(32)
                .withDescription("Nowy Marcelin")
                .withElevator(true)
                .withFloorNumber(5)
                .withListOfApartments(apartmentTOSet)
                .build();

        BuildingTO foundBuilding = buildingService.addNewBuilding(buildingTO);

        ApartmentTO apartmentTO1 = new ApartmentTOBuilder()
                .withAddress("Murawa")
                .withStatus("FREE")
                .withRoomNo(2)
                .withFloor(2)
                .withBalconyNo(2)
                .withBuildingId(foundBuilding.getId())
                .withApartmentSize(75.19)
                .withApartmentPrice(440000.00)
                .build();

        ApartmentTO apartmentTO2 = new ApartmentTOBuilder()
                .withAddress("Murawa")
                .withStatus("RESERVATION")
                .withRoomNo(3)
                .withFloor(0)
                .withBalconyNo(0)
                .withBuildingId(foundBuilding.getId())
                .withApartmentSize(67.89)
                .withApartmentPrice(385000.00)
                .build();

        ApartmentTO apartmentTO3 = new ApartmentTOBuilder()
                .withAddress("Murawa")
                .withStatus("RESERVATION")
                .withRoomNo(5)
                .withFloor(5)
                .withBalconyNo(1)
                .withBuildingId(foundBuilding.getId())
                .withApartmentSize(92.19)
                .withApartmentPrice(687000.00)
                .build();

        ApartmentTO apartmentTO4 = new ApartmentTOBuilder()
                .withAddress("Murawa")
                .withStatus("RESERVATION")
                .withRoomNo(1)
                .withFloor(0)
                .withBalconyNo(2)
                .withBuildingId(foundBuilding.getId())
                .withApartmentSize(32.81)
                .withApartmentPrice(227000.00)
                .build();

        ApartmentTO apartmentTO5 = new ApartmentTOBuilder()
                .withAddress("Murawa")
                .withStatus("BOUGHT")
                .withRoomNo(3)
                .withFloor(4)
                .withBalconyNo(1)
                .withBuildingId(foundBuilding.getId())
                .withApartmentSize(54.19)
                .withApartmentPrice(379000.00)
                .build();

        ApartmentTO apartmentTO6 = new ApartmentTOBuilder()
                .withAddress("Murawa")
                .withStatus("BOUGHT")
                .withRoomNo(2)
                .withFloor(3)
                .withBalconyNo(1)
                .withBuildingId(foundBuilding.getId())
                .withApartmentSize(48.94)
                .withApartmentPrice(440000.00)
                .build();

        ClientTO clientTO1 = new ClientTO.ClientTOBuilder()
                .withFirstName("Talar")
                .withLastName("Sadalla")
                .withAddress("Ptasia")
                .withPhoneNumber("643 454 321")
                .withApartmentIds(apartmentTOSet)
                .build();

        ClientTO clientTO2 = new ClientTO.ClientTOBuilder()
                .withFirstName("Marcin")
                .withLastName("Janusz")
                .withAddress("Ptasia")
                .withPhoneNumber("643 454 321")
                .withApartmentIds(apartmentTOSet)
                .build();

        //when

        ApartmentTO foundApartment1 = apartmentService.addNewApartment(apartmentTO1);
        ApartmentTO foundApartment2 = apartmentService.addNewApartment(apartmentTO2);
        ApartmentTO foundApartment3 = apartmentService.addNewApartment(apartmentTO3);
        ApartmentTO foundApartment4 = apartmentService.addNewApartment(apartmentTO4);
        ApartmentTO foundApartment5 = apartmentService.addNewApartment(apartmentTO5);
        ApartmentTO foundApartment6 = apartmentService.addNewApartment(apartmentTO6);

        apartmentTOSet.add(foundApartment1.getId());
        apartmentTOSet.add(foundApartment2.getId());
        apartmentTOSet.add(foundApartment3.getId());
        apartmentTOSet.add(foundApartment4.getId());


        ClientTO savedClient1 = clientService.addNewClient(clientTO1);
        ClientTO savedClient2 = clientService.addNewClient(clientTO2);

        savedClient1.setApartmentIdSet(apartmentTOSet);
        savedClient1 = clientService.updateClient(savedClient1);

        apartmentTOSet.add(foundApartment5.getId());
        apartmentTOSet.add(foundApartment6.getId());
        foundBuilding.setListOfApartments(apartmentTOSet);
        foundBuilding = buildingService.updateBuilding(foundBuilding);

        savedClient2.setApartmentIdSet(apartmentTOSet);
        savedClient2 = clientService.updateClient(savedClient2);

        savedClient1 = clientService.makeApartmentReservation(savedClient1, foundApartment5);


        // then
        assertEquals(5, savedClient1.getApartmentIdSet().size());
        assertEquals(6, savedClient2.getApartmentIdSet().size());
    }

    @Test
    @Transactional
    public void shouldCountApartmentPriceBoughtByClient() {

        //given
        List<Long> apartmentTOSet = new ArrayList<>();

        BuildingTO buildingTO = new BuildingTOBuilder()
                .withLocalization("Poznan")
                .withApartmentNumber(32)
                .withDescription("Nowy Marcelin")
                .withElevator(true)
                .withFloorNumber(5)
                .withListOfApartments(apartmentTOSet)
                .build();

        BuildingTO foundBuilding = buildingService.addNewBuilding(buildingTO);

        ApartmentTO apartmentTO1 = new ApartmentTOBuilder()
                .withAddress("Murawa")
                .withStatus("FREE")
                .withRoomNo(2)
                .withFloor(2)
                .withBalconyNo(2)
                .withBuildingId(foundBuilding.getId())
                .withApartmentSize(75.19)
                .withApartmentPrice(440000.00)
                .build();

        ApartmentTO apartmentTO2 = new ApartmentTOBuilder()
                .withAddress("Murawa")
                .withStatus("BOUGHT")
                .withRoomNo(3)
                .withFloor(0)
                .withBalconyNo(0)
                .withBuildingId(foundBuilding.getId())
                .withApartmentSize(67.89)
                .withApartmentPrice(385000.00)
                .build();

        ApartmentTO apartmentTO3 = new ApartmentTOBuilder()
                .withAddress("Murawa")
                .withStatus("RESERVATION")
                .withRoomNo(5)
                .withFloor(5)
                .withBalconyNo(1)
                .withBuildingId(foundBuilding.getId())
                .withApartmentSize(92.19)
                .withApartmentPrice(687000.00)
                .build();

        ApartmentTO apartmentTO4 = new ApartmentTOBuilder()
                .withAddress("Murawa")
                .withStatus("RESERVATION")
                .withRoomNo(1)
                .withFloor(0)
                .withBalconyNo(2)
                .withBuildingId(foundBuilding.getId())
                .withApartmentSize(32.81)
                .withApartmentPrice(227000.00)
                .build();

        ApartmentTO apartmentTO5 = new ApartmentTOBuilder()
                .withAddress("Murawa")
                .withStatus("BOUGHT")
                .withRoomNo(3)
                .withFloor(4)
                .withBalconyNo(1)
                .withBuildingId(foundBuilding.getId())
                .withApartmentSize(54.19)
                .withApartmentPrice(379000.00)
                .build();

        ApartmentTO apartmentTO6 = new ApartmentTOBuilder()
                .withAddress("Murawa")
                .withStatus("BOUGHT")
                .withRoomNo(2)
                .withFloor(3)
                .withBalconyNo(1)
                .withBuildingId(foundBuilding.getId())
                .withApartmentSize(48.94)
                .withApartmentPrice(440000.00)
                .build();

        ClientTO clientTO1 = new ClientTO.ClientTOBuilder()
                .withFirstName("Talar")
                .withLastName("Sadalla")
                .withAddress("Ptasia")
                .withPhoneNumber("643 454 321")
                .withApartmentIds(apartmentTOSet)
                .build();

        ClientTO clientTO2 = new ClientTO.ClientTOBuilder()
                .withFirstName("Marcin")
                .withLastName("Janusz")
                .withAddress("Ptasia")
                .withPhoneNumber("643 454 321")
                .withApartmentIds(apartmentTOSet)
                .build();

        //when

        ApartmentTO foundApartment1 = apartmentService.addNewApartment(apartmentTO1);
        ApartmentTO foundApartment2 = apartmentService.addNewApartment(apartmentTO2);
        ApartmentTO foundApartment3 = apartmentService.addNewApartment(apartmentTO3);
        ApartmentTO foundApartment4 = apartmentService.addNewApartment(apartmentTO4);
        ApartmentTO foundApartment5 = apartmentService.addNewApartment(apartmentTO5);
        ApartmentTO foundApartment6 = apartmentService.addNewApartment(apartmentTO6);

        apartmentTOSet.add(foundApartment1.getId());
        apartmentTOSet.add(foundApartment2.getId());
        apartmentTOSet.add(foundApartment3.getId());
        apartmentTOSet.add(foundApartment4.getId());


        ClientTO savedClient1 = clientService.addNewClient(clientTO1);
        ClientTO savedClient2 = clientService.addNewClient(clientTO2);

        savedClient1.setApartmentIdSet(apartmentTOSet);
        savedClient1 = clientService.updateClient(savedClient1);

        apartmentTOSet.add(foundApartment5.getId());
        apartmentTOSet.add(foundApartment6.getId());
        foundBuilding.setListOfApartments(apartmentTOSet);
        foundBuilding = buildingService.updateBuilding(foundBuilding);

        savedClient2.setApartmentIdSet(apartmentTOSet);
        savedClient2 = clientService.updateClient(savedClient2);

        Double apartmentsPriceForClient1 = clientService.sumOfBoughtApartmentsPriceForSpecifiedClient(savedClient1.getId());
        Double apartmentsPriceForClient2 = clientService.sumOfBoughtApartmentsPriceForSpecifiedClient(savedClient2.getId());


        // then
        assertEquals(foundApartment2.getApartmentPrice(), apartmentsPriceForClient1.doubleValue(), 1.0);
        assertEquals(foundApartment2.getApartmentPrice() +
                foundApartment5.getApartmentPrice() +
                foundApartment6.getApartmentPrice(), apartmentsPriceForClient2.doubleValue(), 1.0);
    }

    @Test
    @Transactional
    public void shouldFindClientsThatBoughtMoreThanOneApartment() {

        //given
        List<Long> apartmentTOSet = new ArrayList<>();

        BuildingTO buildingTO = new BuildingTOBuilder()
                .withLocalization("Poznan")
                .withApartmentNumber(32)
                .withDescription("Nowy Marcelin")
                .withElevator(true)
                .withFloorNumber(5)
                .withListOfApartments(apartmentTOSet)
                .build();

        BuildingTO foundBuilding = buildingService.addNewBuilding(buildingTO);

        ApartmentTO apartmentTO1 = new ApartmentTOBuilder()
                .withAddress("Murawa")
                .withStatus("FREE")
                .withRoomNo(2)
                .withFloor(2)
                .withBalconyNo(2)
                .withBuildingId(foundBuilding.getId())
                .withApartmentSize(75.19)
                .withApartmentPrice(440000.00)
                .build();

        ApartmentTO apartmentTO2 = new ApartmentTOBuilder()
                .withAddress("Murawa")
                .withStatus("BOUGHT")
                .withRoomNo(3)
                .withFloor(0)
                .withBalconyNo(0)
                .withBuildingId(foundBuilding.getId())
                .withApartmentSize(67.89)
                .withApartmentPrice(385000.00)
                .build();

        ApartmentTO apartmentTO3 = new ApartmentTOBuilder()
                .withAddress("Murawa")
                .withStatus("RESERVATION")
                .withRoomNo(5)
                .withFloor(5)
                .withBalconyNo(1)
                .withBuildingId(foundBuilding.getId())
                .withApartmentSize(92.19)
                .withApartmentPrice(687000.00)
                .build();

        ApartmentTO apartmentTO4 = new ApartmentTOBuilder()
                .withAddress("Murawa")
                .withStatus("RESERVATION")
                .withRoomNo(1)
                .withFloor(0)
                .withBalconyNo(2)
                .withBuildingId(foundBuilding.getId())
                .withApartmentSize(32.81)
                .withApartmentPrice(227000.00)
                .build();

        ApartmentTO apartmentTO5 = new ApartmentTOBuilder()
                .withAddress("Murawa")
                .withStatus("BOUGHT")
                .withRoomNo(3)
                .withFloor(4)
                .withBalconyNo(1)
                .withBuildingId(foundBuilding.getId())
                .withApartmentSize(54.19)
                .withApartmentPrice(379000.00)
                .build();

        ApartmentTO apartmentTO6 = new ApartmentTOBuilder()
                .withAddress("Murawa")
                .withStatus("BOUGHT")
                .withRoomNo(2)
                .withFloor(3)
                .withBalconyNo(1)
                .withBuildingId(foundBuilding.getId())
                .withApartmentSize(48.94)
                .withApartmentPrice(440000.00)
                .build();

        ClientTO clientTO1 = new ClientTO.ClientTOBuilder()
                .withFirstName("Talar")
                .withLastName("Sadalla")
                .withAddress("Ptasia")
                .withPhoneNumber("643 454 321")
                .withApartmentIds(apartmentTOSet)
                .build();

        ClientTO clientTO2 = new ClientTO.ClientTOBuilder()
                .withFirstName("Marcin")
                .withLastName("Janusz")
                .withAddress("Ptasia")
                .withPhoneNumber("643 454 321")
                .withApartmentIds(apartmentTOSet)
                .build();

        //when

        ApartmentTO foundApartment1 = apartmentService.addNewApartment(apartmentTO1);
        ApartmentTO foundApartment2 = apartmentService.addNewApartment(apartmentTO2);
        ApartmentTO foundApartment3 = apartmentService.addNewApartment(apartmentTO3);
        ApartmentTO foundApartment4 = apartmentService.addNewApartment(apartmentTO4);
        ApartmentTO foundApartment5 = apartmentService.addNewApartment(apartmentTO5);
        ApartmentTO foundApartment6 = apartmentService.addNewApartment(apartmentTO6);

        apartmentTOSet.add(foundApartment1.getId());
        apartmentTOSet.add(foundApartment2.getId());
        apartmentTOSet.add(foundApartment3.getId());
        apartmentTOSet.add(foundApartment4.getId());


        ClientTO savedClient1 = clientService.addNewClient(clientTO1);
        ClientTO savedClient2 = clientService.addNewClient(clientTO2);

        savedClient1.setApartmentIdSet(apartmentTOSet);
        savedClient1 = clientService.updateClient(savedClient1);

        apartmentTOSet.add(foundApartment5.getId());
        apartmentTOSet.add(foundApartment6.getId());
        foundBuilding.setListOfApartments(apartmentTOSet);
        foundBuilding = buildingService.updateBuilding(foundBuilding);

        savedClient2.setApartmentIdSet(apartmentTOSet);
        savedClient2 = clientService.updateClient(savedClient2);

       List<ClientTO> clientTOs=clientService.clientsThatBoughtMoreThanOneApartment();

        // then
        assertEquals(2,clientTOs.size());
        assertEquals("Talar",clientTOs.get(0).getFirstName());
        assertEquals("Janusz",clientTOs.get(1).getLastName());
    }
}
