package com.capgemini.mappers;

import com.capgemini.domain.ApartmentEntity;
import com.capgemini.types.ApartmentTO;
import com.capgemini.types.ApartmentTO.ApartmentTOBuilder;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Apartment mapper
 */
public class ApartmentMapper {

    /**
     * Map Apartment Entity to Apartment Transfer Object
     *
     * @param apartmentEntity apartment entity to map
     * @return mapped apartment entity to transfer object
     */
    public static ApartmentTO toApartmentTO(ApartmentEntity apartmentEntity) {
        if (apartmentEntity == null)
            return null;

        return new ApartmentTOBuilder()
                .withId(apartmentEntity.getId())
                .withVersionId(apartmentEntity.getVersion())
                .withAddress(apartmentEntity.getAddress())
                .withApartmentPrice(apartmentEntity.getApartmentPrice())
                .withApartmentSize(apartmentEntity.getApartmentSize())
                .withBalconyNo(apartmentEntity.getBalconyNo())
                .withBuildingId(apartmentEntity.getBuildingEntity().getId())
                .withRoomNo(apartmentEntity.getRoomNo())
                .withFloor(apartmentEntity.getFloor())
                .withStatus(apartmentEntity.getStatus())
                .build();
    }

    /**
     * Map Apartment Transfer Object to Apartment Entity
     *
     * @param apartmentTO apartment TO to map
     * @return mapped apartment transfer object to entity
     */
    public static ApartmentEntity toApartmentEntity(ApartmentTO apartmentTO) {
        if (apartmentTO == null)
            return null;
        ApartmentEntity apartmentEntity = new ApartmentEntity();
        apartmentEntity.setId(apartmentTO.getId());
        apartmentEntity.setVersion(apartmentTO.getVersion());
        apartmentEntity.setAddress(apartmentTO.getAddress());
        apartmentEntity.setApartmentPrice(apartmentTO.getApartmentPrice());
        apartmentEntity.setApartmentSize(apartmentTO.getApartmentSize());
        apartmentEntity.setBalconyNo(apartmentTO.getBalconyNo());
        apartmentEntity.setFloor(apartmentTO.getFloor());
        apartmentEntity.setRoomNo(apartmentTO.getRoomNo());
        apartmentEntity.setStatus(apartmentTO.getStatus());
        return apartmentEntity;
    }

    /**
     * Map list Apartment Entity to Apartment Transfer Object
     *
     * @param apartmentEntities apartment entity to map
     * @return mapped list of apartment entity to list of transfer object
     */
    public static List<ApartmentTO> map2TOs(List<ApartmentEntity> apartmentEntities) {
        return apartmentEntities.stream().map(ApartmentMapper::toApartmentTO).collect(Collectors.toList());
    }

    /**
     * Map list of Apartment Transfer Object to list Apartment Entity
     *
     * @param apartmentTOs apartment TO to map
     * @return mapped list of apartment transfer object to list of entities
     */
    public static List<ApartmentEntity> map2Entities(List<ApartmentTO> apartmentTOs) {
        return apartmentTOs.stream().map(ApartmentMapper::toApartmentEntity).collect(Collectors.toList());
    }

}
