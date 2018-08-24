package com.capgemini.mappers;

import com.capgemini.domain.ApartmentEntity;
import com.capgemini.domain.ClientEntity;
import com.capgemini.types.ClientTO;
import com.capgemini.types.ClientTO.ClientTOBuilder;

import java.util.List;
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
                        .collect(Collectors.toList()))
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

    public static List<ClientTO> map2TOs(List<ClientEntity> clientEntities) {
        return clientEntities.stream().map(ClientMapper::toClientTO).collect(Collectors.toList());
    }

    public static List<ClientEntity> map2Entities(List<ClientTO> clientTOs) {
        return clientTOs.stream().map(ClientMapper::toClientEntity).collect(Collectors.toList());
    }

}
