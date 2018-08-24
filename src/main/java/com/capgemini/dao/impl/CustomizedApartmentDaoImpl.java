package com.capgemini.dao.impl;

import com.capgemini.dao.CustomizedApartmentDao;
import com.capgemini.domain.ApartmentEntity;
import com.capgemini.types.ApartmentSearchCriteriaTO;

import javax.persistence.TypedQuery;
import java.util.List;

public class CustomizedApartmentDaoImpl extends AbstractDaoImpl<ApartmentEntity, Long> implements CustomizedApartmentDao {
    @Override
    public List<ApartmentEntity> findApartmentsByCriteria(ApartmentSearchCriteriaTO apartmentSearchCriteriaTO) {
        StringBuilder queryBuilder = new StringBuilder();
        boolean andFlagNeeded = false;
        queryBuilder.append("select a from ApartmentEntity a where ");

        if (apartmentSearchCriteriaTO.getMinApartmentSize() != null) {
            queryBuilder.append("a.apartmentSize >= :minApartmentSize");
            andFlagNeeded = true;
        }
        if (apartmentSearchCriteriaTO.getMaxApartmentSize() != null) {
            if (andFlagNeeded) {
                queryBuilder.append(" and ");
            }
            queryBuilder.append("a.apartmentSize <= :maxApartmentSize");
            andFlagNeeded = true;
        }
        if (apartmentSearchCriteriaTO.getMinRoomNo() != null) {
            if (andFlagNeeded) {
                queryBuilder.append(" and ");
            }
            queryBuilder.append("a.roomNo >= :minRoomNo");
            andFlagNeeded = true;
        }
        if (apartmentSearchCriteriaTO.getMaxRoomsNo() != null) {
            if (andFlagNeeded) {
                queryBuilder.append(" and ");
            }
            queryBuilder.append("a.roomNo <= :maxRoomNo");
            andFlagNeeded = true;
        }
        if (apartmentSearchCriteriaTO.getMinBalconyNo() != null) {
            if (andFlagNeeded) {
                queryBuilder.append(" and ");
            }
            queryBuilder.append("a.balconyNo >= :minBalconyNo");
            andFlagNeeded = true;
        }
        if (apartmentSearchCriteriaTO.getMaxBalconyNo() != null) {
            if (andFlagNeeded) {
                queryBuilder.append(" and ");
            }
            queryBuilder.append("a.balconyNo <= :maxBalconyNo");
        }

        TypedQuery<ApartmentEntity> query = entityManager.createQuery(queryBuilder.toString(), ApartmentEntity.class);

        if (apartmentSearchCriteriaTO.getMinApartmentSize() != null) {
            query.setParameter("minApartmentSize", apartmentSearchCriteriaTO.getMinApartmentSize());
        }
        if (apartmentSearchCriteriaTO.getMaxApartmentSize() != null) {
            query.setParameter("maxApartmentSize", apartmentSearchCriteriaTO.getMaxApartmentSize());
        }
        if (apartmentSearchCriteriaTO.getMinRoomNo() != null) {
            query.setParameter("minRoomNo", apartmentSearchCriteriaTO.getMinRoomNo());
        }
        if (apartmentSearchCriteriaTO.getMaxRoomsNo() != null) {
            query.setParameter("maxRoomNo", apartmentSearchCriteriaTO.getMaxRoomsNo());
        }
        if (apartmentSearchCriteriaTO.getMinBalconyNo() != null) {
            query.setParameter("minBalconyNo", apartmentSearchCriteriaTO.getMinBalconyNo());
        }
        if (apartmentSearchCriteriaTO.getMaxBalconyNo() != null) {
            query.setParameter("maxBalconyNo", apartmentSearchCriteriaTO.getMaxBalconyNo());
        }

        return query.getResultList();
    }
}
