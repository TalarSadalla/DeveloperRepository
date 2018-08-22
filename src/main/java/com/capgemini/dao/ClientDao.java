package com.capgemini.dao;

import com.capgemini.domain.ApartmentEntity;
import com.capgemini.domain.ClientEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;

public interface ClientDao extends CrudRepository<ClientEntity, Long> {

    ClientEntity findClientByFirstName(String firstName);

    ClientEntity findClientByLastName(String lastName);

    ClientEntity findClientByPhoneNumber(String phoneNumber);

    ClientEntity findClientByAddress(String address);

    ClientEntity findClientByApartmentEntitySet(Set<ApartmentEntity> apartmentEntitySet);

    @Query("select count(c.id) from ClientEntity c join c.apartmentEntitySet aes where c.id = :client_id and aes.status = :status")
    Integer findNumberOfClientApartmentsWithSpecifiedStatus(@Param("client_id") Long clientId, @Param("status") String status);
}
