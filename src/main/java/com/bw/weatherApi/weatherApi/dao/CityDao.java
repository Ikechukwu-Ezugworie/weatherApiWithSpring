package com.bw.weatherApi.weatherApi.dao;

import com.bw.weatherApi.weatherApi.models.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CityDao extends JpaRepository<City,Long> {
    Optional<City> findByName(String name);
}
