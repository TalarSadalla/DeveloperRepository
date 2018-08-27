package com.capgemini.service;


import com.capgemini.exceptions.NoSuchApartmentException;
import com.capgemini.exceptions.NoSuchBuildingException;
import com.capgemini.types.ApartmentTO;
import com.capgemini.types.ApartmentTO.ApartmentTOBuilder;
import com.capgemini.types.BuildingTO;
import com.capgemini.types.BuildingTO.BuildingTOBuilder;
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
import static org.junit.Assert.assertNull;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = "spring.profiles.active=hsql")
public class BuildingServiceTest {

    @Autowired
    private BuildingService buildingService;

    @Autowired
    private ApartmentService apartmentService;

    @Test
    @Transactional
    public void testShouldAddBuilding() {

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
        apartmentTOSet.add(apartmentTO.getId());

        BuildingTO buildingTO = new BuildingTOBuilder()
                .withLocalization("Poznan")
                .withApartmentNumber(32)
                .withDescription("Nowy Marcelin")
                .withElevator(true)
                .withFloorNumber(5)
                .withListOfApartments(apartmentTOSet)
                .build();

        //when

        BuildingTO foundBuilding = buildingService.addNewBuilding(buildingTO);
        //then
        assertEquals("Poznan", foundBuilding.getLocalization());

    }

    @Test
    @Transactional
    public void testShouldUpdateBuilding() throws NoSuchBuildingException {

        //given

        List<Long> listOfApartmentsId = new ArrayList<>();

        BuildingTO buildingTO = new BuildingTOBuilder()
                .withLocalization("Poznan")
                .withApartmentNumber(32)
                .withDescription("Nowy Marcelin")
                .withElevator(true)
                .withFloorNumber(5)
                .withListOfApartments(listOfApartmentsId)
                .build();

        BuildingTO foundBuilding = new BuildingTO();

        foundBuilding = buildingService.addNewBuilding(buildingTO);


        ApartmentTO apartmentTO = new ApartmentTOBuilder()
                .withAddress("Murawa")
                .withStatus("FREE")
                .withRoomNo(10)
                .withFloor(2)
                .withBalconyNo(2)
                .withBuildingId(foundBuilding.getId())
                .withApartmentSize(75.19)
                .withApartmentPrice(440000.00)
                .build();

        ApartmentTO apartmentTO2 = new ApartmentTOBuilder()
                .withAddress("Murawa")
                .withStatus("FREE")
                .withRoomNo(8)
                .withFloor(1)
                .withBalconyNo(1)
                .withBuildingId(foundBuilding.getId())
                .withApartmentSize(32.17)
                .withApartmentPrice(275000.00)
                .build();

        ApartmentTO foundApartment = new ApartmentTO();

        foundApartment = apartmentService.addNewApartment(apartmentTO);

        Set<Long> apartmentTOSet = new HashSet<>();
        apartmentTOSet.add(foundApartment.getId());
        //when

        ApartmentTO foundApartment2 = new ApartmentTO();

        foundApartment2 = apartmentService.addNewApartment(apartmentTO2);

        apartmentTOSet.add(foundApartment2.getId());


        BuildingTO foundBuilding2 = new BuildingTO();

        foundBuilding2 = buildingService.findBuildingById(foundBuilding.getId());
        foundBuilding2.setFloorNo(7);
        foundBuilding2 = buildingService.updateBuilding(foundBuilding2);


        //then
        assertEquals("Poznan", foundBuilding.getLocalization());

        assertEquals((Integer) 32, foundBuilding.getApartmentNo());
        assertEquals(2, foundBuilding2.getListOfApartments().size());
        assertEquals((Integer) 7, foundBuilding2.getFloorNo());
    }

    @Test(expected = NoSuchBuildingException.class)
    @Transactional
    public void testShouldDeleteBuilding() throws NoSuchBuildingException {

        //given
        List<Long> listOfApartmentsId = new ArrayList<>();

        BuildingTO buildingTO = new BuildingTOBuilder()
                .withLocalization("Poznan")
                .withApartmentNumber(32)
                .withDescription("Nowy Marcelin")
                .withElevator(true)
                .withFloorNumber(5)
                .withListOfApartments(listOfApartmentsId)
                .build();

        BuildingTO foundBuilding = new BuildingTO();
        foundBuilding = buildingService.addNewBuilding(buildingTO);


        ApartmentTO apartmentTO = new ApartmentTOBuilder()
                .withAddress("Murawa")
                .withStatus("FREE")
                .withRoomNo(10)
                .withFloor(2)
                .withBalconyNo(2)
                .withBuildingId(foundBuilding.getId())
                .withApartmentSize(75.19)
                .withApartmentPrice(440000.00)
                .build();

        ApartmentTO apartmentTO2 = new ApartmentTOBuilder()
                .withAddress("Murawa")
                .withStatus("FREE")
                .withRoomNo(8)
                .withFloor(1)
                .withBalconyNo(1)
                .withBuildingId(foundBuilding.getId())
                .withApartmentSize(32.17)
                .withApartmentPrice(275000.00)
                .build();

        ApartmentTO foundApartment = new ApartmentTO();

        foundApartment = apartmentService.addNewApartment(apartmentTO);

        List<Long> apartmentTOSet = new ArrayList<>();
        apartmentTOSet.add(foundApartment.getId());

        //when

        ApartmentTO foundApartment2 = new ApartmentTO();

        foundApartment2 = apartmentService.addNewApartment(apartmentTO2);
        buildingService.deleteBuilding(foundBuilding);


        apartmentTOSet.add(foundApartment2.getId());

        BuildingTO updatedBuildingTO = new BuildingTOBuilder()
                .withLocalization(foundBuilding.getLocalization())
                .withApartmentNumber(foundBuilding.getApartmentNo())
                .withDescription("Updated building")
                .withElevator(foundBuilding.isElevator())
                .withFloorNumber(foundBuilding.getFloorNo())
                .withListOfApartments(apartmentTOSet)
                .build();
        BuildingTO foundBuilding2 = new BuildingTO();

        foundBuilding2 = buildingService.addNewBuilding(updatedBuildingTO);
        foundBuilding = buildingService.findBuildingById(foundBuilding.getId());


        //then
        assertNull(foundBuilding.getId());
        assertEquals("Updated building", updatedBuildingTO.getDescription());
        assertEquals(2, foundBuilding.getListOfApartments().size());
    }

    @Test(expected = NoSuchApartmentException.class)
    @Transactional
    public void testShouldDeleteBuildingWithCascade() throws NoSuchBuildingException, NoSuchApartmentException {

        //given
        List<Long> listOfApartmentsId = new ArrayList<>();

        BuildingTO buildingTO = new BuildingTOBuilder()
                .withLocalization("Poznan")
                .withApartmentNumber(32)
                .withDescription("Nowy Marcelin")
                .withElevator(true)
                .withFloorNumber(5)
                .withListOfApartments(listOfApartmentsId)
                .build();

        BuildingTO foundBuilding = new BuildingTO();
        foundBuilding = buildingService.addNewBuilding(buildingTO);


        ApartmentTO apartmentTO = new ApartmentTOBuilder()
                .withAddress("Murawa")
                .withStatus("FREE")
                .withRoomNo(10)
                .withFloor(2)
                .withBalconyNo(2)
                .withBuildingId(foundBuilding.getId())
                .withApartmentSize(75.19)
                .withApartmentPrice(440000.00)
                .build();

        ApartmentTO apartmentTO2 = new ApartmentTOBuilder()
                .withAddress("Murawa")
                .withStatus("FREE")
                .withRoomNo(8)
                .withFloor(1)
                .withBalconyNo(1)
                .withBuildingId(foundBuilding.getId())
                .withApartmentSize(32.17)
                .withApartmentPrice(275000.00)
                .build();

        //when
        ApartmentTO foundApartment = apartmentService.addNewApartment(apartmentTO);
        ApartmentTO foundApartment2 = apartmentService.addNewApartment(apartmentTO2);


        BuildingTO foundBuilding2 = buildingService.findBuildingById(foundBuilding.getId());
        buildingService.deleteBuilding(foundBuilding);
        foundApartment = apartmentService.findApartmentById(foundApartment.getId());
    }


    @Test
    @Transactional
    public void testShouldFindBuildingById() throws NoSuchBuildingException {

        //given
        List<Long> listOfApartmentsId = new ArrayList<>();

        BuildingTO buildingTO = new BuildingTOBuilder()
                .withLocalization("Poznan")
                .withApartmentNumber(32)
                .withDescription("Nowy Marcelin")
                .withElevator(true)
                .withFloorNumber(5)
                .withListOfApartments(listOfApartmentsId)
                .build();

        BuildingTO savedBuilding = new BuildingTO();
        savedBuilding = buildingService.addNewBuilding(buildingTO);


        ApartmentTO apartmentTO = new ApartmentTOBuilder()
                .withAddress("Murawa")
                .withStatus("FREE")
                .withRoomNo(10)
                .withFloor(2)
                .withBalconyNo(2)
                .withBuildingId(savedBuilding.getId())
                .withApartmentSize(75.19)
                .withApartmentPrice(440000.00)
                .build();

        ApartmentTO apartmentTO2 = new ApartmentTOBuilder()
                .withAddress("Murawa")
                .withStatus("FREE")
                .withRoomNo(8)
                .withFloor(1)
                .withBalconyNo(1)
                .withBuildingId(savedBuilding.getId())
                .withApartmentSize(32.17)
                .withApartmentPrice(275000.00)
                .build();

        ApartmentTO foundApartment = new ApartmentTO();

        foundApartment = apartmentService.addNewApartment(apartmentTO);

        List<Long> apartmentTOSet = new ArrayList<>();
        apartmentTOSet.add(foundApartment.getId());

        //when

        ApartmentTO foundApartment2 = new ApartmentTO();

        foundApartment2 = apartmentService.addNewApartment(apartmentTO2);

        apartmentTOSet.add(foundApartment2.getId());

        BuildingTO updatedBuildingTO = new BuildingTOBuilder()
                .withLocalization(savedBuilding.getLocalization())
                .withApartmentNumber(savedBuilding.getApartmentNo())
                .withDescription("Updated building")
                .withElevator(savedBuilding.isElevator())
                .withFloorNumber(savedBuilding.getFloorNo())
                .withListOfApartments(apartmentTOSet)
                .build();
        BuildingTO foundBuilding2 = new BuildingTO();

        foundBuilding2 = buildingService.addNewBuilding(updatedBuildingTO);
        BuildingTO foundBuilding = buildingService.findBuildingById(savedBuilding.getId());


        //then
        assertEquals(savedBuilding.getId(), foundBuilding.getId());
        assertEquals(savedBuilding.getFloorNo(), foundBuilding.getFloorNo());
    }

    @Test
    @Transactional
    public void shouldFindNumberOfApartmentsWithSpecifiedStatus() throws NoSuchBuildingException, NoSuchApartmentException {

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
                .withAddress("Poznan")
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
        apartmentTOSet.add(foundApartment5.getId());
        apartmentTOSet.add(foundApartment6.getId());

        Double noOfFreeApartments = buildingService.numberOfApartmentsInSpecifiedBuildingWithSpecifiedStatus(foundBuilding.getId(), "FREE");
        Double noOfBoughtApartments = buildingService.numberOfApartmentsInSpecifiedBuildingWithSpecifiedStatus(foundBuilding.getId(), "BOUGHT");
        Double noOfReservedApartments = buildingService.numberOfApartmentsInSpecifiedBuildingWithSpecifiedStatus(foundBuilding.getId(), "RESERVATION");

        //then
        assertEquals(1, noOfFreeApartments.doubleValue(), 1.0);
        assertEquals(3, noOfBoughtApartments.doubleValue(), 1.0);
        assertEquals(2, noOfBoughtApartments.doubleValue(), 1.0);
    }

    @Test(expected = NoSuchApartmentException.class)
    @Transactional
    public void shouldNotFindAnyApartmentsWithSpecifiedStatus() throws NoSuchBuildingException, NoSuchApartmentException {

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
                .withAddress("Poznan")
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
        apartmentTOSet.add(foundApartment5.getId());
        apartmentTOSet.add(foundApartment6.getId());

        Double noOfFreeApartments = buildingService.numberOfApartmentsInSpecifiedBuildingWithSpecifiedStatus(foundBuilding.getId(), "RANDOM");

    }

    @Test(expected = NoSuchBuildingException.class)
    @Transactional
    public void shouldNotFindAnyApartmentsWithSpecifiedStatusInBuildingThatNotExist() throws NoSuchBuildingException, NoSuchApartmentException {

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
                .withAddress("Poznan")
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
        apartmentTOSet.add(foundApartment5.getId());
        apartmentTOSet.add(foundApartment6.getId());

        Double noOfFreeApartments = buildingService.numberOfApartmentsInSpecifiedBuildingWithSpecifiedStatus(foundBuilding.getId() + 1, "RANDOM");

        //then
        assertEquals(1, noOfFreeApartments.doubleValue(), 1.0);
    }

    @Test
    @Transactional
    public void shouldFindAveragePriceOfApartmentInSpecifiedBuilding() throws NoSuchBuildingException {

        //given
        List<Long> apartmentTOSet = new ArrayList<>();

        BuildingTO buildingTO = new BuildingTOBuilder()
                .withLocalization("Poznan")
                .withApartmentNumber(32)
                .withDescription("Nowy Marcelin")
                .withElevator(false)
                .withFloorNumber(5)
                .withListOfApartments(apartmentTOSet)
                .build();

        BuildingTO foundBuilding = buildingService.addNewBuilding(buildingTO);

        ApartmentTO apartmentTO1 = new ApartmentTOBuilder()
                .withAddress("Poznan")
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

        apartmentTOSet.add(foundApartment5.getId());
        apartmentTOSet.add(foundApartment6.getId());

        List<ApartmentTO> listOfApartments = new ArrayList<>();
        listOfApartments.add(foundApartment1);
        listOfApartments.add(foundApartment2);
        listOfApartments.add(foundApartment3);
        listOfApartments.add(foundApartment4);
        listOfApartments.add(foundApartment5);
        listOfApartments.add(foundApartment6);


        Double apartmentAvgSum = 0.0;
        for (int i = 0; i < apartmentTOSet.size(); i++) {
            apartmentAvgSum = apartmentAvgSum + listOfApartments.get(i).getApartmentPrice();
        }

        //then
        assertEquals(apartmentAvgSum / listOfApartments.size(), buildingService.averagePriceOfApartmentsInSpecifiedBuilding(foundBuilding.getId()), 1.0);
    }

    @Test
    @Transactional
    public void shouldFindFreeApartments() throws NoSuchBuildingException {

        //given
        List<Long> apartmentTOSet = new ArrayList<>();

        BuildingTO buildingTO = new BuildingTOBuilder()
                .withLocalization("Poznan")
                .withApartmentNumber(15)
                .withDescription("Nowy Marcelin")
                .withElevator(false)
                .withFloorNumber(3)
                .withListOfApartments(apartmentTOSet)
                .build();

        BuildingTO foundBuilding = buildingService.addNewBuilding(buildingTO);

        BuildingTO buildingTO2 = new BuildingTOBuilder()
                .withLocalization("Poznan")
                .withApartmentNumber(32)
                .withDescription("Nowy Marcelin 2")
                .withElevator(true)
                .withFloorNumber(7)
                .withListOfApartments(apartmentTOSet)
                .build();

        BuildingTO foundBuilding2 = buildingService.addNewBuilding(buildingTO2);

        BuildingTO buildingTO3 = new BuildingTOBuilder()
                .withLocalization("Poznan")
                .withApartmentNumber(16)
                .withDescription("Nowy Marcelin 3")
                .withElevator(false)
                .withFloorNumber(4)
                .withListOfApartments(apartmentTOSet)
                .build();

        BuildingTO foundBuilding3 = buildingService.addNewBuilding(buildingTO3);

        ApartmentTO apartmentTO1 = new ApartmentTOBuilder()
                .withAddress("Poznan")
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
                .withBuildingId(foundBuilding2.getId())
                .withApartmentSize(32.81)
                .withApartmentPrice(227000.00)
                .build();

        ApartmentTO apartmentTO5 = new ApartmentTOBuilder()
                .withAddress("Murawa")
                .withStatus("BOUGHT")
                .withRoomNo(3)
                .withFloor(4)
                .withBalconyNo(1)
                .withBuildingId(foundBuilding2.getId())
                .withApartmentSize(54.19)
                .withApartmentPrice(379000.00)
                .build();

        ApartmentTO apartmentTO6 = new ApartmentTOBuilder()
                .withAddress("Murawa")
                .withStatus("FREE")
                .withRoomNo(2)
                .withFloor(3)
                .withBalconyNo(1)
                .withBuildingId(foundBuilding2.getId())
                .withApartmentSize(48.94)
                .withApartmentPrice(440000.00)
                .build();

        ApartmentTO apartmentTO7 = new ApartmentTOBuilder()
                .withAddress("Murawa")
                .withStatus("FREE")
                .withRoomNo(1)
                .withFloor(0)
                .withBalconyNo(2)
                .withBuildingId(foundBuilding3.getId())
                .withApartmentSize(32.81)
                .withApartmentPrice(227000.00)
                .build();

        ApartmentTO apartmentTO8 = new ApartmentTOBuilder()
                .withAddress("Murawa")
                .withStatus("BOUGHT")
                .withRoomNo(3)
                .withFloor(4)
                .withBalconyNo(1)
                .withBuildingId(foundBuilding3.getId())
                .withApartmentSize(54.19)
                .withApartmentPrice(379000.00)
                .build();

        ApartmentTO apartmentTO9 = new ApartmentTOBuilder()
                .withAddress("Murawa")
                .withStatus("FREE")
                .withRoomNo(2)
                .withFloor(3)
                .withBalconyNo(1)
                .withBuildingId(foundBuilding3.getId())
                .withApartmentSize(48.94)
                .withApartmentPrice(440000.00)
                .build();

        //when

        ApartmentTO foundApartment1 = apartmentService.addNewApartment(apartmentTO1);
        ApartmentTO foundApartment2 = apartmentService.addNewApartment(apartmentTO2);
        ApartmentTO foundApartment3 = apartmentService.addNewApartment(apartmentTO3);
        ApartmentTO foundApartment4 = apartmentService.addNewApartment(apartmentTO4);
        ApartmentTO foundApartment5 = apartmentService.addNewApartment(apartmentTO5);
        ApartmentTO foundApartment6 = apartmentService.addNewApartment(apartmentTO6);
        ApartmentTO foundApartment7 = apartmentService.addNewApartment(apartmentTO7);
        ApartmentTO foundApartment8 = apartmentService.addNewApartment(apartmentTO8);
        ApartmentTO foundApartment9 = apartmentService.addNewApartment(apartmentTO9);

        apartmentTOSet.add(foundApartment1.getId());
        apartmentTOSet.add(foundApartment2.getId());
        apartmentTOSet.add(foundApartment3.getId());
        apartmentTOSet.add(foundApartment4.getId());
        apartmentTOSet.add(foundApartment5.getId());
        apartmentTOSet.add(foundApartment6.getId());
        apartmentTOSet.add(foundApartment7.getId());
        apartmentTOSet.add(foundApartment8.getId());
        apartmentTOSet.add(foundApartment9.getId());

        List<ApartmentTO> listOfApartments = new ArrayList<>();
        listOfApartments.add(foundApartment1);
        listOfApartments.add(foundApartment6);
        listOfApartments.add(foundApartment7);
        listOfApartments.add(foundApartment9);

        List<BuildingTO>listOfBuildings=buildingService.findBuildingWithMostFreeApartments();

        //then
        assertEquals(1,listOfBuildings.size());
        assertEquals(foundBuilding3.getApartmentNo(),listOfBuildings.get(0).getApartmentNo());
    }

    @Test
    @Transactional
    public void shouldFindTwoBuildingsWithMostFreeApartments() throws NoSuchBuildingException {

        //given
        List<Long> apartmentTOSet = new ArrayList<>();

        BuildingTO buildingTO = new BuildingTOBuilder()
                .withLocalization("Poznan")
                .withApartmentNumber(15)
                .withDescription("Nowy Marcelin")
                .withElevator(false)
                .withFloorNumber(3)
                .withListOfApartments(apartmentTOSet)
                .build();

        BuildingTO foundBuilding = buildingService.addNewBuilding(buildingTO);

        BuildingTO buildingTO2 = new BuildingTOBuilder()
                .withLocalization("Poznan")
                .withApartmentNumber(32)
                .withDescription("Nowy Marcelin 2")
                .withElevator(true)
                .withFloorNumber(7)
                .withListOfApartments(apartmentTOSet)
                .build();

        BuildingTO foundBuilding2 = buildingService.addNewBuilding(buildingTO2);

        BuildingTO buildingTO3 = new BuildingTOBuilder()
                .withLocalization("Poznan")
                .withApartmentNumber(16)
                .withDescription("Nowy Marcelin 3")
                .withElevator(false)
                .withFloorNumber(4)
                .withListOfApartments(apartmentTOSet)
                .build();

        BuildingTO foundBuilding3 = buildingService.addNewBuilding(buildingTO3);

        ApartmentTO apartmentTO1 = new ApartmentTOBuilder()
                .withAddress("Poznan")
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
                .withStatus("FREE")
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
                .withBuildingId(foundBuilding2.getId())
                .withApartmentSize(32.81)
                .withApartmentPrice(227000.00)
                .build();

        ApartmentTO apartmentTO5 = new ApartmentTOBuilder()
                .withAddress("Murawa")
                .withStatus("BOUGHT")
                .withRoomNo(3)
                .withFloor(4)
                .withBalconyNo(1)
                .withBuildingId(foundBuilding2.getId())
                .withApartmentSize(54.19)
                .withApartmentPrice(379000.00)
                .build();

        ApartmentTO apartmentTO6 = new ApartmentTOBuilder()
                .withAddress("Murawa")
                .withStatus("FREE")
                .withRoomNo(2)
                .withFloor(3)
                .withBalconyNo(1)
                .withBuildingId(foundBuilding2.getId())
                .withApartmentSize(48.94)
                .withApartmentPrice(440000.00)
                .build();

        ApartmentTO apartmentTO7 = new ApartmentTOBuilder()
                .withAddress("Murawa")
                .withStatus("FREE")
                .withRoomNo(1)
                .withFloor(0)
                .withBalconyNo(2)
                .withBuildingId(foundBuilding3.getId())
                .withApartmentSize(32.81)
                .withApartmentPrice(227000.00)
                .build();

        ApartmentTO apartmentTO8 = new ApartmentTOBuilder()
                .withAddress("Murawa")
                .withStatus("BOUGHT")
                .withRoomNo(3)
                .withFloor(4)
                .withBalconyNo(1)
                .withBuildingId(foundBuilding3.getId())
                .withApartmentSize(54.19)
                .withApartmentPrice(379000.00)
                .build();

        ApartmentTO apartmentTO9 = new ApartmentTOBuilder()
                .withAddress("Murawa")
                .withStatus("FREE")
                .withRoomNo(2)
                .withFloor(3)
                .withBalconyNo(1)
                .withBuildingId(foundBuilding3.getId())
                .withApartmentSize(48.94)
                .withApartmentPrice(440000.00)
                .build();

        //when

        ApartmentTO foundApartment1 = apartmentService.addNewApartment(apartmentTO1);
        ApartmentTO foundApartment2 = apartmentService.addNewApartment(apartmentTO2);
        ApartmentTO foundApartment3 = apartmentService.addNewApartment(apartmentTO3);
        ApartmentTO foundApartment4 = apartmentService.addNewApartment(apartmentTO4);
        ApartmentTO foundApartment5 = apartmentService.addNewApartment(apartmentTO5);
        ApartmentTO foundApartment6 = apartmentService.addNewApartment(apartmentTO6);
        ApartmentTO foundApartment7 = apartmentService.addNewApartment(apartmentTO7);
        ApartmentTO foundApartment8 = apartmentService.addNewApartment(apartmentTO8);
        ApartmentTO foundApartment9 = apartmentService.addNewApartment(apartmentTO9);

        apartmentTOSet.add(foundApartment1.getId());
        apartmentTOSet.add(foundApartment2.getId());
        apartmentTOSet.add(foundApartment3.getId());
        apartmentTOSet.add(foundApartment4.getId());
        apartmentTOSet.add(foundApartment5.getId());
        apartmentTOSet.add(foundApartment6.getId());
        apartmentTOSet.add(foundApartment7.getId());
        apartmentTOSet.add(foundApartment8.getId());
        apartmentTOSet.add(foundApartment9.getId());

        List<ApartmentTO> listOfApartments = new ArrayList<>();
        listOfApartments.add(foundApartment1);
        listOfApartments.add(foundApartment6);
        listOfApartments.add(foundApartment7);
        listOfApartments.add(foundApartment9);

        List<BuildingTO>listOfBuildings=buildingService.findBuildingWithMostFreeApartments();

        //then
        assertEquals(2,listOfBuildings.size());
        assertEquals(foundBuilding.getApartmentNo(),listOfBuildings.get(0).getApartmentNo());
        assertEquals(foundBuilding3.getApartmentNo(),listOfBuildings.get(1).getApartmentNo());
    }

    @Test(expected = NoSuchBuildingException.class)
    @Transactional
    public void shouldNotFindFreeApartments() throws NoSuchBuildingException {

        //given
        List<Long> apartmentTOSet = new ArrayList<>();

        BuildingTO buildingTO = new BuildingTOBuilder()
                .withLocalization("Poznan")
                .withApartmentNumber(15)
                .withDescription("Nowy Marcelin")
                .withElevator(false)
                .withFloorNumber(3)
                .withListOfApartments(apartmentTOSet)
                .build();

        BuildingTO foundBuilding = buildingService.addNewBuilding(buildingTO);

        BuildingTO buildingTO2 = new BuildingTOBuilder()
                .withLocalization("Poznan")
                .withApartmentNumber(32)
                .withDescription("Nowy Marcelin 2")
                .withElevator(true)
                .withFloorNumber(7)
                .withListOfApartments(apartmentTOSet)
                .build();

        BuildingTO foundBuilding2 = buildingService.addNewBuilding(buildingTO2);

        BuildingTO buildingTO3 = new BuildingTOBuilder()
                .withLocalization("Poznan")
                .withApartmentNumber(16)
                .withDescription("Nowy Marcelin 3")
                .withElevator(false)
                .withFloorNumber(4)
                .withListOfApartments(apartmentTOSet)
                .build();

        BuildingTO foundBuilding3 = buildingService.addNewBuilding(buildingTO3);

        ApartmentTO apartmentTO1 = new ApartmentTOBuilder()
                .withAddress("Poznan")
                .withStatus("RESERVATION")
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
                .withStatus("BOUGHT")
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
                .withBuildingId(foundBuilding2.getId())
                .withApartmentSize(32.81)
                .withApartmentPrice(227000.00)
                .build();

        ApartmentTO apartmentTO5 = new ApartmentTOBuilder()
                .withAddress("Murawa")
                .withStatus("BOUGHT")
                .withRoomNo(3)
                .withFloor(4)
                .withBalconyNo(1)
                .withBuildingId(foundBuilding2.getId())
                .withApartmentSize(54.19)
                .withApartmentPrice(379000.00)
                .build();

        ApartmentTO apartmentTO6 = new ApartmentTOBuilder()
                .withAddress("Murawa")
                .withStatus("BOUGHT")
                .withRoomNo(2)
                .withFloor(3)
                .withBalconyNo(1)
                .withBuildingId(foundBuilding2.getId())
                .withApartmentSize(48.94)
                .withApartmentPrice(440000.00)
                .build();

        ApartmentTO apartmentTO7 = new ApartmentTOBuilder()
                .withAddress("Murawa")
                .withStatus("BOUGHT")
                .withRoomNo(1)
                .withFloor(0)
                .withBalconyNo(2)
                .withBuildingId(foundBuilding3.getId())
                .withApartmentSize(32.81)
                .withApartmentPrice(227000.00)
                .build();

        ApartmentTO apartmentTO8 = new ApartmentTOBuilder()
                .withAddress("Murawa")
                .withStatus("BOUGHT")
                .withRoomNo(3)
                .withFloor(4)
                .withBalconyNo(1)
                .withBuildingId(foundBuilding3.getId())
                .withApartmentSize(54.19)
                .withApartmentPrice(379000.00)
                .build();

        ApartmentTO apartmentTO9 = new ApartmentTOBuilder()
                .withAddress("Murawa")
                .withStatus("RESERVATION")
                .withRoomNo(2)
                .withFloor(3)
                .withBalconyNo(1)
                .withBuildingId(foundBuilding3.getId())
                .withApartmentSize(48.94)
                .withApartmentPrice(440000.00)
                .build();

        //when

        ApartmentTO foundApartment1 = apartmentService.addNewApartment(apartmentTO1);
        ApartmentTO foundApartment2 = apartmentService.addNewApartment(apartmentTO2);
        ApartmentTO foundApartment3 = apartmentService.addNewApartment(apartmentTO3);
        ApartmentTO foundApartment4 = apartmentService.addNewApartment(apartmentTO4);
        ApartmentTO foundApartment5 = apartmentService.addNewApartment(apartmentTO5);
        ApartmentTO foundApartment6 = apartmentService.addNewApartment(apartmentTO6);
        ApartmentTO foundApartment7 = apartmentService.addNewApartment(apartmentTO7);
        ApartmentTO foundApartment8 = apartmentService.addNewApartment(apartmentTO8);
        ApartmentTO foundApartment9 = apartmentService.addNewApartment(apartmentTO9);

        apartmentTOSet.add(foundApartment1.getId());
        apartmentTOSet.add(foundApartment2.getId());
        apartmentTOSet.add(foundApartment3.getId());
        apartmentTOSet.add(foundApartment4.getId());
        apartmentTOSet.add(foundApartment5.getId());
        apartmentTOSet.add(foundApartment6.getId());
        apartmentTOSet.add(foundApartment7.getId());
        apartmentTOSet.add(foundApartment8.getId());
        apartmentTOSet.add(foundApartment9.getId());

        List<ApartmentTO> listOfApartments = new ArrayList<>();
        listOfApartments.add(foundApartment1);
        listOfApartments.add(foundApartment6);
        listOfApartments.add(foundApartment7);
        listOfApartments.add(foundApartment9);

        List<BuildingTO>listOfBuildings=buildingService.findBuildingWithMostFreeApartments();

    }

}
