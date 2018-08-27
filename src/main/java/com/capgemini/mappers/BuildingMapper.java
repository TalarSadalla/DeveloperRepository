package com.capgemini.mappers;

import com.capgemini.domain.ApartmentEntity;
import com.capgemini.domain.BuildingEntity;
import com.capgemini.types.BuildingTO;
import com.capgemini.types.BuildingTO.BuildingTOBuilder;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Building mapper
 */
public class BuildingMapper {

    /**
     * Map Building Entity to Building Transfer Object
     *
     * @param buildingEntity buliding entity to map
     * @return mapped buliding entity to transfer object
     */
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

    /**
     * Map Building Transfer Object to Building Entity
     *
     * @param buildingTO building TO to map
     * @return mapped building transfer object to entity
     */
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

    /**
     * Map list Building Entity to Building Transfer Object
     *
     * @param buildingEntities building entity to map
     * @return mapped list of building entity to list of transfer object
     */
    public static List<BuildingTO> map2TOs(List<BuildingEntity> buildingEntities) {
        return buildingEntities.stream().map(BuildingMapper::toBuildingTO).collect(Collectors.toList());
    }

    /**
     * Map list of Building Transfer Object to list Building Entity
     *
     * @param buildingTOs building TO to map
     * @return mapped list of building transfer object to list of entities
     */
    public static List<BuildingEntity> map2Entities(List<BuildingTO> buildingTOs) {
        return buildingTOs.stream().map(BuildingMapper::toBuildingEntity).collect(Collectors.toList());
    }

}
