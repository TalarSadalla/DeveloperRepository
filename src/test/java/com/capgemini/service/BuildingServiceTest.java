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
    public void testShouldUpdateBuilding() {

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
    public void testShouldDeleteBuilding() {

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
    public void testShouldDeleteBuildingWithCascade() {

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
        List<Long> apartmentTOSet = new ArrayList<>();
        ApartmentTO foundApartment = apartmentService.addNewApartment(apartmentTO);
        ApartmentTO foundApartment2 = apartmentService.addNewApartment(apartmentTO2);

        apartmentTOSet.add(foundApartment.getId());
        foundBuilding.setListOfApartments(apartmentTOSet);
        foundBuilding=buildingService.updateBuilding(foundBuilding);

        apartmentTOSet.add(foundApartment2.getId());

        BuildingTO updatedBuildingTO = new BuildingTOBuilder()
                .withLocalization(foundBuilding.getLocalization())
                .withApartmentNumber(foundBuilding.getApartmentNo())
                .withDescription("Updated building")
                .withElevator(foundBuilding.isElevator())
                .withFloorNumber(foundBuilding.getFloorNo())
                .withListOfApartments(apartmentTOSet)
                .build();

        BuildingTO  foundBuilding2 = buildingService.addNewBuilding(updatedBuildingTO);
        buildingService.deleteBuilding(foundBuilding);
        //foundBuilding = buildingService.findBuildingById(foundBuilding.getId());
        foundApartment=apartmentService.findApartmentById(foundApartment.getId());
    }


    @Test
    @Transactional
    public void testShouldFindBuildingById() {

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

}
