package com.capgemini.dao;

import com.capgemini.domain.ApartmentEntity;
import com.capgemini.domain.ClientEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

public interface ClientDao extends CrudRepository<ClientEntity, Long> {

    ClientEntity findClientByFirstName(String firstName);

    ClientEntity findClientByLastName(String lastName);

    ClientEntity findClientByPhoneNumber(String phoneNumber);

    ClientEntity findClientByAddress(String address);

    ClientEntity findClientByApartmentEntitySet(List<ApartmentEntity> apartmentEntitySet);

    @Query("select c from ClientEntity c join c.apartmentEntitySet aes on aes.id = :apartmentId")
    List<ClientEntity> findClientWithApartment(@Param("apartmentId") Long apartmentId);

    @Query("select sum(aes.apartmentPrice) from ClientEntity c " +
            "join c.apartmentEntitySet aes on c.id = :clientId " +
            "where aes.status=BOUGHT")
    Double sumOfBoughtApartmentsPriceForSpecifiedCLient(@Param("clientId") Long apartmentId);

    @Query("select c from ClientEntity c join c.apartmentEntitySet aes on c.id = :clientId where aes.status=BOUGHT AND count(c.apartmentEntitySet.size)>1")
    List<ClientEntity> clientsThatBoughtMoreThanOneApartment(@Param("clientId") Long apartmentId);
}
