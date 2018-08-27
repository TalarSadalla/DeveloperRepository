package com.capgemini.mappers;

import com.capgemini.domain.ApartmentEntity;
import com.capgemini.domain.ClientEntity;
import com.capgemini.types.ClientTO;
import com.capgemini.types.ClientTO.ClientTOBuilder;

import java.util.List;
import java.util.stream.Collectors;

;

public class ClientMapper {

    /**
     * Map Client Entity to Client Transfer Object
     *
     * @param clientEntity client entity to map
     * @return mapped client entity to transfer object
     */
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

    /**
     * Map Client Transfer Object to Client Entity
     *
     * @param clientTO client TO to map
     * @return mapped client transfer object to entity
     */
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

    /**
     * Map list Client Entity to Client Transfer Object
     *
     * @param clientEntities client entity to map
     * @return mapped list of client entity to list of transfer object
     */
    public static List<ClientTO> map2TOs(List<ClientEntity> clientEntities) {
        return clientEntities.stream().map(ClientMapper::toClientTO).collect(Collectors.toList());
    }

    /**
     * Map list of Client Transfer Object to list Client Entity
     *
     * @param clientTOs client TO to map
     * @return mapped list of client transfer object to list of entities
     */
    public static List<ClientEntity> map2Entities(List<ClientTO> clientTOs) {
        return clientTOs.stream().map(ClientMapper::toClientEntity).collect(Collectors.toList());
    }

}
