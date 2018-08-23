package com.capgemini.dao.impl;

import com.capgemini.dao.AbstractDao;
import com.capgemini.dao.CustomizedApartmentDao;
import com.capgemini.domain.ApartmentEntity;
import com.capgemini.types.ApartmentSearchCriteriaTO;

import javax.persistence.TypedQuery;
import java.util.List;

public class CustomizedApartmentDaoImpl extends AbstractDaoImpl<ApartmentEntity, Long> implements CustomizedApartmentDao {
    @Override
    public List<ApartmentEntity> findApartmentsByCriteria(ApartmentSearchCriteriaTO apartmentSearchCriteriaTO) {
        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append("select a from ApartmentEntity a where ");
        boolean canAppendQueryByAnd = false;

        if (apartmentSearchCriteriaTO.getMinApartmentSize() != null) {
            queryBuilder.append("a.apartmentSize >= :minApartmentSize");
            canAppendQueryByAnd = true;
        }
        if (apartmentSearchCriteriaTO.getMaxApartmentSize() != null) {
            if (canAppendQueryByAnd) {
                queryBuilder.append(" and ");
            }
            queryBuilder.append("a.apartmentSize <= :maxApartmentSize");
            canAppendQueryByAnd = true;
        }
        if (apartmentSearchCriteriaTO.getMinRoomNo() != null) {
            if (canAppendQueryByAnd) {
                queryBuilder.append(" and ");
            }
            queryBuilder.append("a.roomNo >= :minRoomNo");
            canAppendQueryByAnd = true;
        }
        if (apartmentSearchCriteriaTO.getMaxRoomsNo() != null) {
            if (canAppendQueryByAnd) {
                queryBuilder.append(" and ");
            }
            queryBuilder.append("a.roomNo <= :maxRoomNo");
            canAppendQueryByAnd = true;
        }
        if (apartmentSearchCriteriaTO.getMinBalconyNo() != null) {
            if (canAppendQueryByAnd) {
                queryBuilder.append(" and ");
            }
            queryBuilder.append("a.balconyNo >= :minBalconyNo");
            canAppendQueryByAnd = true;
        }
        if (apartmentSearchCriteriaTO.getMaxBalconyNo() != null) {
            if (canAppendQueryByAnd) {
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
