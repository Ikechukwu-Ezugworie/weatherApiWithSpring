/**

    Author: Oluwatobi Adenekan
    email:  tadenekan@byteworks.com.ng
    organisation: Byteworks Technology solution

**/
package com.bw.weatherApi.weatherApi.models;


import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;


@Entity
public class PortalUser {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false)
    private String username;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String authKey;
    @Column(nullable = false)
    private String firstName;
    @Column(nullable = false)
    private String lastName;
    private Timestamp dateCreated;
    @Column(nullable = false)
    private  Timestamp dateUpdated;


    @OneToOne(fetch = FetchType.LAZY)
    private City selectedSityToEmail;

    @ManyToOne(fetch = FetchType.LAZY)
    private Role role;


//   @JoinTable(name = "portalUser_city",
//   joinColumns = {@JoinColumn(name = "portalUserId")},
//           inverseJoinColumns = {@JoinColumn(name = "city_id")}
//   ) // to define the join tables

    @ManyToMany(fetch = FetchType.LAZY)
    @LazyCollection(value = LazyCollectionOption.TRUE )
    private Set<City> cities = new HashSet<>();
    public PortalUser() {
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<City> getCities() {
        return cities;
    }

    public void setCities(Set<City> cities) {
        this.cities = cities;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAuthKey() {
        return authKey;
    }

    public void setAuthKey(String authKey) {
        this.authKey = authKey;
    }

    public Timestamp getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Timestamp dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Timestamp getDateUpdated() {
        return dateUpdated;
    }

    public void setDateUpdated(Timestamp dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public City getSelectedSityToEmail() {
        return selectedSityToEmail;
    }

    public void setSelectedSityToEmail(City selectedSityToEmail) {
        this.selectedSityToEmail = selectedSityToEmail;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
