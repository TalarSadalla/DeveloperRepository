package com.capgemini.dao;

import com.capgemini.domain.ApartmentEntity;
import com.capgemini.domain.ClientEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

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
            "where aes.status=:status")
    Double sumOfBoughtApartmentsPriceForSpecifiedCLient(@Param("clientId") Long clientId, @Param("status") String status);

    @Query("select c from ClientEntity c join c.apartmentEntitySet aes " +
            "where (select count(*) from aes where upper(aes.status) LIKE 'BOUGHT')>1 " +
            "Group by c.id")
    List<ClientEntity> clientsThatBoughtMoreThanOneApartment();
}
