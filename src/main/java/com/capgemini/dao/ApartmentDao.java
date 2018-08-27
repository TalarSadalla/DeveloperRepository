package com.capgemini.dao;

import com.capgemini.domain.ApartmentEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface ApartmentDao extends CrudRepository<ApartmentEntity, Long>, CustomizedApartmentDao {

    /**
     *  Find apartment by address
     * @param address apartment address
     * @return Apartment with specified address
     */
    ApartmentEntity findApartmentByAddress(String address);

    /**
     *  Find list of apartments for disabled people
     *  (building must have an elevator, or if it doesn't the apartment must be on floor 0)
     * @return List of apartments that fulfill criteria
     */
    @Query("select ae from ApartmentEntity ae join ae.building " +
            "where ae.building.isElevator = 'true' or ae.floor = 0")
    List<ApartmentEntity> findApartmentsForDisabledClients();
}
