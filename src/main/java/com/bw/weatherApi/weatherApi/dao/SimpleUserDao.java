package com.bw.weatherApi.weatherApi.dao;

import com.bw.weatherApi.weatherApi.models.City;
import com.bw.weatherApi.weatherApi.models.PortalUser;
import com.bw.weatherApi.weatherApi.models.SimpleUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SimpleUserDao extends JpaRepository<SimpleUser, Long> {
    SimpleUser findByEmail(String email);
    Optional<SimpleUser> findById(Long id);
    Optional<SimpleUser> findByCity(City city);
    List<SimpleUser> findByPortalUser(PortalUser portalUser);
}
