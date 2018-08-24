package com.capgemini.mappers;

import com.capgemini.domain.ApartmentEntity;
import com.capgemini.types.ApartmentTO;
import com.capgemini.types.ApartmentTO.ApartmentTOBuilder;

import java.util.List;
import java.util.stream.Collectors;

;

public class ApartmentMapper {

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

    public static List<ApartmentTO> map2TOs(List<ApartmentEntity> apartmentEntities) {
        return apartmentEntities.stream().map(ApartmentMapper::toApartmentTO).collect(Collectors.toList());
    }

    public static List<ApartmentEntity> map2Entities(List<ApartmentTO> apartmentTOs) {
        return apartmentTOs.stream().map(ApartmentMapper::toApartmentEntity).collect(Collectors.toList());
    }

}
