/**

    Author: Oluwatobi Adenekan
    email:  tadenekan@byteworks.com.ng
    organisation: Byteworks Technology solution

**/
package com.bw.weatherApi.weatherApi.dto;

import com.bw.weatherApi.validator.numberValidator.EnsureNumber;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.NumberFormat;

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
   @EnsureNumber(message = "Enter a valid role id")
    private Long roleId;

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

    public Long getRoleId() {
        return roleId;
    }

    public SignUpRequestDto setRoleId(Long roleId) {
        this.roleId = roleId;
        return this;
    }

    public String getPortalAccountName() {
        return portalAccountName;
    }

    public void setPortalAccountName(String portalAccountName) {
        this.portalAccountName = portalAccountName;
    }
}
