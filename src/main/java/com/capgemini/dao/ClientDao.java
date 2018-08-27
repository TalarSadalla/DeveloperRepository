package com.capgemini.dao;

import com.capgemini.domain.ApartmentEntity;
import com.capgemini.domain.ClientEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Client Dao with Crud Repository
 */
public interface ClientDao extends CrudRepository<ClientEntity, Long> {

    /**
     * Find client by first name
     * @param firstName clients first name
     * @return Client with specified first name
     */
    ClientEntity findClientByFirstName(String firstName);

    /**
     * Find client by last name
     * @param lastName clients last name
     * @return Client with specified last name
     */
    ClientEntity findClientByLastName(String lastName);

    /**
     * Find client with specified phone number
     * @param phoneNumber client phone number
     * @return Client with specified phone number
     */
    ClientEntity findClientByPhoneNumber(String phoneNumber);

    /**
     * Find client by address
     * @param address clients address
     * @return Client with specified address
     */
    ClientEntity findClientByAddress(String address);

    /**
     * Find client by apartments he owned
     * @param apartmentEntitySet list of clients apartments
     * @return Client with specified list of apartments
     */
    ClientEntity findClientByApartmentEntitySet(List<ApartmentEntity> apartmentEntitySet);

    /**
     * Find Owners of specified apartment
     * @param apartmentId Apartment id
     * @return List of owners(Clients) that are the owners of the apartment
     */
    @Query("select c from ClientEntity c join c.apartmentEntitySet aes on aes.id = :apartmentId")
    List<ClientEntity> findClientWithApartment(@Param("apartmentId") Long apartmentId);

    /**
     * Find sum of apartments price that are bought by specified Client
     * @param clientId client Id
     * @param status apartment status, by default set as 'BOUGHT'
     * @return Sum of apartments price
     */
    @Query("select sum(aes.apartmentPrice) from ClientEntity c " +
            "join c.apartmentEntitySet aes on c.id = :clientId " +
            "where aes.status=:status")
    Double sumOfBoughtApartmentsPriceForSpecifiedCLient(@Param("clientId") Long clientId, @Param("status") String status);

    /**
     * Find clients that bought more than one apartment
     * @return List of Clients that bought more than one apartment
     */
    @Query("select c from ClientEntity c join c.apartmentEntitySet aes " +
            "where (select count(*) from aes where upper(aes.status) LIKE 'BOUGHT')>1 " +
            "Group by c.id")
    List<ClientEntity> clientsThatBoughtMoreThanOneApartment();
}
