package com.bw.weatherApi.weatherApi.dao;

import com.bw.weatherApi.weatherApi.models.City;
import com.bw.weatherApi.weatherApi.models.PortalUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface CityDao extends JpaRepository<City,Long> {
    Optional<City> findByName(String name);

    List<City> findByIdInOrderByNameAsc(List<Long> id);


}
