package com.capgemini.service;

import com.capgemini.exceptions.NoSuchBuildingException;
import com.capgemini.types.BuildingTO;

import java.util.List;


public interface BuildingService {

    BuildingTO addNewBuilding(BuildingTO buildingTO);

    BuildingTO updateBuilding(BuildingTO buildingTO);

    void deleteBuilding(BuildingTO buildingTO) throws NoSuchBuildingException;

    BuildingTO findBuildingById(Long id) throws NoSuchBuildingException;

    BuildingTO findBuildingByLocalization(String localization) throws NoSuchBuildingException;

    Double numberOfApartmentsInSpecifiedBuildingWithSpecifiedStatus(Long buildingId, String status);

    Double averagePriceOfApartmentsInSpecifiedBuilding(Long buildingId);
}
