package com.capgemini.mappers;

import com.capgemini.domain.ApartmentEntity;
import com.capgemini.domain.BuildingEntity;
import com.capgemini.types.BuildingTO;
import com.capgemini.types.BuildingTO.BuildingTOBuilder;

import java.util.Set;
import java.util.stream.Collectors;

;

public class BuildingMapper {

    public static BuildingTO toBuildingTO(BuildingEntity buildingEntity) {
        if (buildingEntity == null)
            return null;

        return new BuildingTOBuilder()
                .withVersionId(buildingEntity.getVersion())
                .withId(buildingEntity.getId())
                .withDescription(buildingEntity.getDescription())
                .withLocalization(buildingEntity.getLocalization())
                .withFloorNumber(buildingEntity.getFloorNo())
                .withElevator(buildingEntity.isElevator())
                .withApartmentNumber(buildingEntity.getApartmentNo())
                .withListOfApartments(buildingEntity.getListOfApartments()
                        .stream()
                        .map(ApartmentEntity::getId)
                        .collect(Collectors.toSet()))
                .build();

    }

    public static BuildingEntity toBuildingEntity(BuildingTO buildingTO) {
        if (buildingTO == null)
            return null;
        BuildingEntity buildingEntity = new BuildingEntity();
        buildingEntity.setVersion(buildingTO.getVersion());
        buildingEntity.setId(buildingTO.getId());
        buildingEntity.setDescription(buildingTO.getDescription());
        buildingEntity.setLocalization(buildingTO.getLocalization());
        buildingEntity.setFloorNo(buildingTO.getFloorNo());
        buildingEntity.setElevator(buildingTO.isElevator());
        buildingEntity.setApartmentNo(buildingTO.getApartmentNo());
        return buildingEntity;
    }

    public static Set<BuildingTO> map2TOs(Set<BuildingEntity> buildingEntities) {
        return buildingEntities.stream().map(BuildingMapper::toBuildingTO).collect(Collectors.toSet());
    }

    public static Set<BuildingEntity> map2Entities(Set<BuildingTO> buildingTOs) {
        return buildingTOs.stream().map(BuildingMapper::toBuildingEntity).collect(Collectors.toSet());
    }

}
