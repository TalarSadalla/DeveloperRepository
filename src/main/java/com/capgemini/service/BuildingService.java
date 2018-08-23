package com.capgemini.service;

import com.capgemini.types.ApartmentTO;
import com.capgemini.types.BuildingTO;

import java.util.Set;


public interface BuildingService {

    BuildingTO addNewBuilding(BuildingTO buildingTO);

    BuildingTO updateBuilding(BuildingTO buildingTO);

    void deleteBuilding(BuildingTO buildingTO);

    BuildingTO findBuildingById(Long id);

    BuildingTO findBuildingByLocalization(String localization) ;
}
