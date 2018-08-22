package com.capgemini.service;


import com.capgemini.domain.ApartmentEntity;
import com.capgemini.exceptions.NoSuchBuildingException;
import com.capgemini.types.ApartmentTO;
import com.capgemini.types.ApartmentTO.ApartmentTOBuilder;
import com.capgemini.types.BuildingTO;
import com.capgemini.types.BuildingTO.BuildingTOBuilder;

import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Set;

import static org.junit.Assert.*;

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

        Set<Long> apartmentTOSet = new HashSet<>();
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
        try {
            buildingService.addNewBuilding(buildingTO);
        } catch (Exception e) {
            e.printStackTrace();
        }

        BuildingTO foundBuilding = new BuildingTO();
        try {
            foundBuilding = buildingService.findBuildingByLocalization("Poznan");
        } catch (Exception e) {
            e.printStackTrace();
        }

        //then
        assertEquals("Poznan", foundBuilding.getLocalization());

    }

    @Test
    @Transactional
    public void testShouldUpdateBuilding() {

        //given

        Set<Long> listOfApartmentsId = new HashSet<>();

        BuildingTO buildingTO = new BuildingTOBuilder()
                .withLocalization("Poznan")
                .withApartmentNumber(32)
                .withDescription("Nowy Marcelin")
                .withElevator(true)
                .withFloorNumber(5)
                .withListOfApartments(listOfApartmentsId)
                .build();

        BuildingTO foundBuilding= new BuildingTO();
        try {
            foundBuilding= buildingService.addNewBuilding(buildingTO);
        } catch (Exception e) {
            e.printStackTrace();
        }

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

        try {
            foundApartment= apartmentService.addNewApartment(apartmentTO);
        } catch (Exception e) {
            e.printStackTrace();
        }


        Set<Long> apartmentTOSet = new HashSet<>();
        apartmentTOSet.add(foundApartment.getId());


        //when

        ApartmentTO foundApartment2 = new ApartmentTO();

        try {
            foundApartment2=apartmentService.addNewApartment(apartmentTO2);
        } catch (Exception e) {
            e.printStackTrace();
        }

        apartmentTOSet.add(foundApartment2.getId());

        BuildingTO updatedBuildingTO = new BuildingTOBuilder()
                .withLocalization(foundBuilding.getLocalization())
                .withApartmentNumber(foundBuilding.getApartmentNo())
                .withDescription("Updated building")
                .withElevator(foundBuilding.isElevator())
                .withFloorNumber(foundBuilding.getFloorNo())
                .withListOfApartments(apartmentTOSet)
                .build();
        BuildingTO foundBuilding2= new BuildingTO();
        try {
            foundBuilding2= buildingService.addNewBuilding(updatedBuildingTO);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //then
        assertEquals("Poznan", foundBuilding.getLocalization());

        assertEquals((Integer)32, foundBuilding.getApartmentNo());
        assertEquals("Updated building", updatedBuildingTO.getDescription());
        assertEquals(2, updatedBuildingTO.getListOfApartments().size());
    }

    @Test(expected= NoSuchBuildingException.class)
    @Transactional
    public void testShouldDeleteBuilding() {

        //given
        Set<Long> listOfApartmentsId = new HashSet<>();

        BuildingTO buildingTO = new BuildingTOBuilder()
                .withLocalization("Poznan")
                .withApartmentNumber(32)
                .withDescription("Nowy Marcelin")
                .withElevator(true)
                .withFloorNumber(5)
                .withListOfApartments(listOfApartmentsId)
                .build();

        BuildingTO foundBuilding= new BuildingTO();
        foundBuilding= buildingService.addNewBuilding(buildingTO);


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

        ApartmentTO apartmentTO2 = new ApartmentTOBuilder()
                .withAddress("Murawa")
                .withStatus("FREE")
                .withRoomNo(8)
                .withFloor(1)
                .withBalconyNo(1)
                .withBuildingId((long) 1)
                .withApartmentSize(32.17)
                .withApartmentPrice(275000.00)
                .build();

        ApartmentTO foundApartment = new ApartmentTO();

        foundApartment= apartmentService.addNewApartment(apartmentTO);

        Set<Long> apartmentTOSet = new HashSet<>();
        apartmentTOSet.add(foundApartment.getId());

        //when

        ApartmentTO foundApartment2 = new ApartmentTO();

        foundApartment2=apartmentService.addNewApartment(apartmentTO2);
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
        BuildingTO foundBuilding2= new BuildingTO();

        foundBuilding2= buildingService.addNewBuilding(updatedBuildingTO);
        foundBuilding=buildingService.findBuildingById(foundBuilding.getId());


        //then
        assertNull(foundBuilding.getId());
        assertEquals("Updated building", updatedBuildingTO.getDescription());
        assertEquals(2, foundBuilding.getListOfApartments().size());
    }
}
