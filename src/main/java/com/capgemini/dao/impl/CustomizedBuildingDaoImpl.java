package com.capgemini.dao.impl;

import com.capgemini.dao.CustomizedBuildingDao;
import com.capgemini.domain.ApartmentEntity;
import com.capgemini.domain.BuildingEntity;
import com.capgemini.domain.QApartmentEntity;
import com.capgemini.domain.QBuildingEntity;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQuery;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Custom Building DAO implementation
 */
public class CustomizedBuildingDaoImpl extends AbstractDaoImpl<ApartmentEntity, Long> implements CustomizedBuildingDao {

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Find building with most free apartments
     * @return List of buildings with most free apartments
     */
    @Override
    public List<BuildingEntity> findBuildingWithMostFreeApartments() {

        JPAQuery<BuildingEntity> buildingsWithFreeApartments = new JPAQuery(entityManager);
        JPAQuery<Long> maxNumberOfFreeApartments = new JPAQuery(entityManager);
        QBuildingEntity building = QBuildingEntity.buildingEntity;
        QApartmentEntity apartment = QApartmentEntity.apartmentEntity;

        Long maxCountOfFreeApartments = maxNumberOfFreeApartments.select(apartment.count())
                .from(apartment)
                .where(apartment.status.lower().eq("free"))
                .groupBy(apartment.building)
                .orderBy(apartment.count().desc())
                .fetchFirst();

        if (maxCountOfFreeApartments == null) {
            maxCountOfFreeApartments = 0L;
        }

        List<BuildingEntity> listOfBuildings = buildingsWithFreeApartments.select(building)
                .from(building)
                .where(building.id.in(
                        JPAExpressions.select(apartment.building.id)
                                .from(apartment)
                                .where(apartment.status.lower().eq("free"))
                                .groupBy(apartment.building)
                                .having(apartment.count().eq(maxCountOfFreeApartments))
                        )
                )
                .fetch();
        return listOfBuildings;
    }
}
