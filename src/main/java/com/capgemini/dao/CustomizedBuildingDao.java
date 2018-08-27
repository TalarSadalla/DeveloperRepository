package com.capgemini.dao;

import com.capgemini.domain.BuildingEntity;

import java.util.List;

/**
 * Custom interface for Building Dao
 */
public interface CustomizedBuildingDao {

    /**
     *
     * @return List of buildings with most free apartments
     */
    List<BuildingEntity> findBuildingWithMostFreeApartments();
}
