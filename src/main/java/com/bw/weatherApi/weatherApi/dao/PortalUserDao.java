package com.bw.weatherApi.weatherApi.dao;

import com.bw.weatherApi.weatherApi.models.PortalUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface  PortalUserDao extends JpaRepository<PortalUser, Long> {
    Optional<PortalUser> findByUsername(String username);
}
