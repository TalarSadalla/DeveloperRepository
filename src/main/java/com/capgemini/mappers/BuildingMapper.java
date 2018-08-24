package com.capgemini.mappers;

import com.capgemini.domain.ApartmentEntity;
import com.capgemini.domain.BuildingEntity;
import com.capgemini.types.BuildingTO;
import com.capgemini.types.BuildingTO.BuildingTOBuilder;

import java.util.List;
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
                        .collect(Collectors.toList()))
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

    public static List<BuildingTO> map2TOs(List<BuildingEntity> buildingEntities) {
        return buildingEntities.stream().map(BuildingMapper::toBuildingTO).collect(Collectors.toList());
    }

    public static List<BuildingEntity> map2Entities(List<BuildingTO> buildingTOs) {
        return buildingTOs.stream().map(BuildingMapper::toBuildingEntity).collect(Collectors.toList());
    }

}
