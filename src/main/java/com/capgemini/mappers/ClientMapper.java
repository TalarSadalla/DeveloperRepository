package com.capgemini.mappers;

import com.capgemini.domain.ApartmentEntity;
import com.capgemini.domain.BuildingEntity;
import com.capgemini.domain.ClientEntity;
import com.capgemini.types.BuildingTO;
import com.capgemini.types.BuildingTO.BuildingTOBuilder;
import com.capgemini.types.ClientTO;
import com.capgemini.types.ClientTO.ClientTOBuilder;

import java.util.Set;
import java.util.stream.Collectors;

;

public class ClientMapper {

    public static ClientTO toClientTO(ClientEntity clientEntity) {
        if (clientEntity == null)
            return null;

        return new ClientTOBuilder()
                .withVersionId(clientEntity.getVersion())
                .withId(clientEntity.getId())
                .withFirstName(clientEntity.getFirstName())
                .withLastName(clientEntity.getLastName())
                .withPhoneNumber(clientEntity.getPhoneNumber())
                .withAddress(clientEntity.getAddress())
                .withApartmentIds(clientEntity.getApartmentEntitySet()
                        .stream()
                        .map(ApartmentEntity::getId)
                        .collect(Collectors.toSet()))
                .build();

    }

    public static ClientEntity toClientEntity(ClientTO clientTO) {
        if (clientTO == null)
            return null;
        ClientEntity clientEntity = new ClientEntity();
        clientEntity.setVersion(clientTO.getVersion());
        clientEntity.setId(clientTO.getId());
        clientEntity.setFirstName(clientTO.getFirstName());
        clientEntity.setLastName(clientTO.getLastName());
        clientEntity.setPhoneNumber(clientTO.getPhoneNumber());
        clientEntity.setAddress(clientTO.getAddress());
        return clientEntity;
    }

    public static Set<ClientTO> map2TOs(Set<ClientEntity> clientEntities) {
        return clientEntities.stream().map(ClientMapper::toClientTO).collect(Collectors.toSet());
    }

    public static Set<ClientEntity> map2Entities(Set<ClientTO> clientTOs) {
        return clientTOs.stream().map(ClientMapper::toClientEntity).collect(Collectors.toSet());
    }

}
