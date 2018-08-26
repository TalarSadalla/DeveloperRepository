package com.capgemini.service;

import com.capgemini.exceptions.NoSuchApartmentException;
import com.capgemini.types.ApartmentSearchCriteriaTO;
import com.capgemini.types.ApartmentTO;
import com.capgemini.types.ApartmentTO.ApartmentTOBuilder;
import com.capgemini.types.BuildingTO;
import com.capgemini.types.BuildingTO.BuildingTOBuilder;
import com.capgemini.types.ClientTO;
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
@SpringBootTest(properties = "spring.profiles.active=hsql")
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
                .withAddress("Warszawa")
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

        ApartmentTO foundApartment= apartmentService.findApartmentByAddress(apartmentTO.getAddress());


        //then
        assertEquals("Warszawa", foundApartment.getAddress());
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
    public void testShouldUpdateApartmentOptimisticLocking() {
        //given

        List<Long> listOfApartmentsId = new ArrayList<>();

        BuildingTO buildingTO = new BuildingTOBuilder()
                .withLocalization("Poznan")
                .withApartmentNumber(32)
                .withDescription("Nowy Marcelin")
                .withElevator(false)
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

    @Test
    @Transactional
    public void shouldFindApartmentByAllSearchCriteria() {

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

        apartmentTOSet.add(foundApartment5.getId());
        apartmentTOSet.add(foundApartment6.getId());
        foundBuilding.setListOfApartments(apartmentTOSet);
        foundBuilding = buildingService.updateBuilding(foundBuilding);

        ApartmentSearchCriteriaTO apartmentSearchCriteriaTO=new ApartmentSearchCriteriaTO();
        apartmentSearchCriteriaTO.setMinApartmentSize(10.0);
        apartmentSearchCriteriaTO.setMaxApartmentSize(90.0);
        apartmentSearchCriteriaTO.setMinBalconyNo(1);
        apartmentSearchCriteriaTO.setMaxBalconyNo(3);
        apartmentSearchCriteriaTO.setMinRoomNo(1);
        apartmentSearchCriteriaTO.setMaxRoomsNo(4);
        List<ApartmentTO> apartmentTOs=apartmentService.findApartmentsByCriteria(apartmentSearchCriteriaTO);

         //then
        assertEquals(4, apartmentTOs.size());
    }

    @Test
    @Transactional
    public void shouldFindApartmentByTwoSearchCriteriaBalconyNoAndRoomNo() {

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

        apartmentTOSet.add(foundApartment5.getId());
        apartmentTOSet.add(foundApartment6.getId());
        foundBuilding.setListOfApartments(apartmentTOSet);
        foundBuilding = buildingService.updateBuilding(foundBuilding);

        ApartmentSearchCriteriaTO apartmentSearchCriteriaTO=new ApartmentSearchCriteriaTO();
        apartmentSearchCriteriaTO.setMinBalconyNo(1);
        apartmentSearchCriteriaTO.setMaxBalconyNo(3);
        apartmentSearchCriteriaTO.setMinRoomNo(1);
        apartmentSearchCriteriaTO.setMaxRoomsNo(4);
        List<ApartmentTO> apartmentTOs=apartmentService.findApartmentsByCriteria(apartmentSearchCriteriaTO);

        //then
        assertEquals(4, apartmentTOs.size());
    }

    @Test
    @Transactional
    public void shouldFindApartmentByTwoSearchCriteriaApartmentSizeAndRoomNo() {

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

        apartmentTOSet.add(foundApartment5.getId());
        apartmentTOSet.add(foundApartment6.getId());
        foundBuilding.setListOfApartments(apartmentTOSet);
        foundBuilding = buildingService.updateBuilding(foundBuilding);

        ApartmentSearchCriteriaTO apartmentSearchCriteriaTO=new ApartmentSearchCriteriaTO();
        apartmentSearchCriteriaTO.setMinApartmentSize(30.00);
        apartmentSearchCriteriaTO.setMaxApartmentSize(50.00);
        apartmentSearchCriteriaTO.setMinRoomNo(1);
        apartmentSearchCriteriaTO.setMaxRoomsNo(4);
        List<ApartmentTO> apartmentTOs=apartmentService.findApartmentsByCriteria(apartmentSearchCriteriaTO);

        //then
        assertEquals(2, apartmentTOs.size());
    }

    @Test
    @Transactional
    public void shouldFindApartmentByOneSearchCriteria() {

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

        apartmentTOSet.add(foundApartment5.getId());
        apartmentTOSet.add(foundApartment6.getId());
        foundBuilding.setListOfApartments(apartmentTOSet);
        foundBuilding = buildingService.updateBuilding(foundBuilding);

        ApartmentSearchCriteriaTO apartmentSearchCriteriaTO=new ApartmentSearchCriteriaTO();
        apartmentSearchCriteriaTO.setMinBalconyNo(2);
        apartmentSearchCriteriaTO.setMaxBalconyNo(4);
        List<ApartmentTO> apartmentTOs=apartmentService.findApartmentsByCriteria(apartmentSearchCriteriaTO);

        //then
        assertEquals(3, apartmentTOs.size());
    }

    @Test
    @Transactional
    public void shouldfFindApartmentsForDisabledClients() {

        //given
        List<Long> apartmentTOSet1 = new ArrayList<>();
        List<Long> apartmentTOSet2 = new ArrayList<>();

        BuildingTO buildingTO = new BuildingTOBuilder()
                .withLocalization("Poznan")
                .withApartmentNumber(32)
                .withDescription("Nowy Marcelin")
                .withElevator(true)
                .withFloorNumber(5)
                .withListOfApartments(apartmentTOSet1)
                .build();

        BuildingTO buildingTO2 = new BuildingTOBuilder()
                .withLocalization("Poznan")
                .withApartmentNumber(10)
                .withDescription("Nowy Targ")
                .withElevator(false)
                .withFloorNumber(5)
                .withListOfApartments(apartmentTOSet2)
                .build();

        BuildingTO foundBuilding = buildingService.addNewBuilding(buildingTO);
        BuildingTO foundBuilding2 = buildingService.addNewBuilding(buildingTO2);

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
                .withFloor(0)
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

        //when

        ApartmentTO foundApartment1 = apartmentService.addNewApartment(apartmentTO1);
        ApartmentTO foundApartment2 = apartmentService.addNewApartment(apartmentTO2);
        ApartmentTO foundApartment3 = apartmentService.addNewApartment(apartmentTO3);
        ApartmentTO foundApartment4 = apartmentService.addNewApartment(apartmentTO4);
        ApartmentTO foundApartment5 = apartmentService.addNewApartment(apartmentTO5);
        ApartmentTO foundApartment6 = apartmentService.addNewApartment(apartmentTO6);

        apartmentTOSet1.add(foundApartment1.getId());
        apartmentTOSet1.add(foundApartment2.getId());
        apartmentTOSet1.add(foundApartment3.getId());
        apartmentTOSet2.add(foundApartment4.getId());

        apartmentTOSet2.add(foundApartment5.getId());
        apartmentTOSet2.add(foundApartment6.getId());
        foundBuilding.setListOfApartments(apartmentTOSet1);
        foundBuilding2.setListOfApartments(apartmentTOSet2);
        foundBuilding = buildingService.updateBuilding(foundBuilding);
        foundBuilding2 = buildingService.updateBuilding(foundBuilding2);

        List<ApartmentTO> apartmentTOs2=apartmentService.findApartmentsForDisabledClients();

        //then
        assertEquals(5, apartmentTOs2.size());
    }


}
