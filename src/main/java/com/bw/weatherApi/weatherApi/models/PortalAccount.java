package com.bw.weatherApi.weatherApi.models;

import javax.persistence.*;

@Entity
public class PortalAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    private PortalUser portalUser;



    public PortalAccount() {
    }
}
