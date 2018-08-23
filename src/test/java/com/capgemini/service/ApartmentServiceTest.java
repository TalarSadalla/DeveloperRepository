package com.capgemini.service;

import com.capgemini.exceptions.NoSuchApartmentException;
import com.capgemini.types.ApartmentTO;
import com.capgemini.types.ApartmentTO.ApartmentTOBuilder;
import com.capgemini.types.BuildingTO;
import com.capgemini.types.BuildingTO.BuildingTOBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = "spring.profiles.active=mysql")
public class ApartmentServiceTest {

    @Autowired
    private ApartmentService apartmentService;

    @Autowired
    private BuildingService buildingService;


    @Test
    @Transactional
    public void testShouldAddApartment() {

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

        BuildingTO saveBuilding=buildingService.addNewBuilding(buildingTO);

        ApartmentTO apartmentTO=new ApartmentTOBuilder()
                .withAddress("Murawa")
                .withStatus("FREE")
                .withRoomNo(10)
                .withFloor(2)
                .withBalconyNo(2)
                .withBuildingId(saveBuilding.getId())
                .withApartmentSize(75.19)
                .withApartmentPrice(440000.00)
                .build();
        Set<Long> apartmentTOSet= new HashSet<>();

        //when

        apartmentTOSet.add(apartmentTO.getId());

        apartmentService.addNewApartment(apartmentTO);

        ApartmentTO foundApartment = new ApartmentTO();

          foundApartment= apartmentService.findApartmentByAddress(apartmentTO.getAddress());


        //then
        assertEquals("Murawa", foundApartment.getAddress());
        assertEquals("FREE",foundApartment.getStatus());
        assertEquals((Double)440000.00, foundApartment.getApartmentPrice());
    }

    @Test
    @Transactional
    public void testShouldUpdateApartment() {

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

        BuildingTO saveBuilding=buildingService.addNewBuilding(buildingTO);

        ApartmentTO apartmentTO=new ApartmentTOBuilder()
                .withAddress("Murawa")
                .withStatus("FREE")
                .withRoomNo(10)
                .withFloor(2)
                .withBalconyNo(2)
                .withBuildingId(saveBuilding.getId())
                .withApartmentSize(75.19)
                .withApartmentPrice(440000.00)
                .build();
        Set<Long> apartmentTOSet= new HashSet<>();
        //when

            buildingService.addNewBuilding(buildingTO);

        apartmentTOSet.add(apartmentTO.getId());

            apartmentService.addNewApartment(apartmentTO);

        ApartmentTO foundApartment = new ApartmentTO();
        foundApartment= apartmentService.findApartmentByAddress(apartmentTO.getAddress());


        ApartmentTO updatedApartmentTO=new ApartmentTOBuilder() .withAddress("Murawa")
                .withId(foundApartment.getId())
                .withStatus("BOUGHT")
                .withRoomNo(foundApartment.getRoomNo())
                .withFloor(foundApartment.getFloor())
                .withBalconyNo(foundApartment.getBalconyNo())
                .withBuildingId(foundApartment.getBuildingId())
                .withApartmentSize(foundApartment.getApartmentSize())
                .withApartmentPrice(435000.00)
                .build();

       apartmentService.updateApartment(updatedApartmentTO);


        //then
        assertEquals("Murawa", foundApartment.getAddress());
        assertEquals("FREE",foundApartment.getStatus());
        assertEquals((Double)440000.00, foundApartment.getApartmentPrice());


        assertEquals("BOUGHT",updatedApartmentTO.getStatus());
        assertEquals((Double)435000.00, updatedApartmentTO.getApartmentPrice());
    }

    @Test(expected = OptimisticLockingFailureException.class)
    @Transactional
    public void testShouldUpdateApartmentOptimisticLocking() {
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

        BuildingTO saveBuilding=buildingService.addNewBuilding(buildingTO);

        ApartmentTO apartmentTO=new ApartmentTOBuilder()
                .withAddress("Murawa")
                .withStatus("FREE")
                .withRoomNo(10)
                .withFloor(2)
                .withBalconyNo(2)
                .withBuildingId(saveBuilding.getId())
                .withApartmentSize(75.19)
                .withApartmentPrice(440000.00)
                .build();
        Set<Long> apartmentTOSet= new HashSet<>();

        //when
        buildingService.addNewBuilding(buildingTO);


        apartmentTOSet.add(apartmentTO.getId());

        ApartmentTO foundApartment=apartmentService.addNewApartment(apartmentTO);
        ApartmentTO f1=apartmentService.findApartmentById(foundApartment.getId());
        f1.setAddress("Poland");
        ApartmentTO f2=apartmentService.findApartmentById(foundApartment.getId());
        f2.setAddress("Warszawa");
        f1=apartmentService.updateApartment(f1);
        apartmentService.updateApartment(f2);
    }

    @Test(expected = NoSuchApartmentException.class)
    @Transactional
    public void testShouldDeleteApartment() {
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

        BuildingTO saveBuilding=buildingService.addNewBuilding(buildingTO);

        ApartmentTO apartmentTO=new ApartmentTOBuilder()
                .withAddress("Murawa")
                .withStatus("FREE")
                .withRoomNo(10)
                .withFloor(2)
                .withBalconyNo(2)
                .withBuildingId(saveBuilding.getId())
                .withApartmentSize(75.19)
                .withApartmentPrice(440000.00)
                .build();
        Set<Long> apartmentTOSet= new HashSet<>();

        //when
        buildingService.addNewBuilding(buildingTO);


        apartmentTOSet.add(apartmentTO.getId());

        ApartmentTO foundApartment=apartmentService.addNewApartment(apartmentTO);
        apartmentService.deleteApartment(foundApartment);
        foundApartment=apartmentService.findApartmentById(foundApartment.getId());
    }
}
