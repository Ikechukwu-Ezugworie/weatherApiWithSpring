/**

    Author: Oluwatobi Adenekan
    email:  tadenekan@byteworks.com.ng
    organisation: Byteworks Technology solution

**/
package com.bw.weatherApi.weatherApi.dto;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

public class SignUpRequestDto {
    @NotBlank
    private String  username;

    @NotBlank
    private  String password;

    @NotBlank
    private  String email;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @NotBlank
    @NotNull
    private String roleId;

    @NotBlank
    private String portalAccountName;

    public SignUpRequestDto() {
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

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getPortalAccountName() {
        return portalAccountName;
    }

    public void setPortalAccountName(String portalAccountName) {
        this.portalAccountName = portalAccountName;
    }
}
