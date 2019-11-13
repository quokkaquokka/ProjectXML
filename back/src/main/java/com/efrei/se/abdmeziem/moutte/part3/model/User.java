/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.efrei.se.abdmeziem.moutte.part3.model;

/**
 *
 * @author QuokkaKoala
 */
public class User {
    
    private String objectID;
    private String name;
    private String firstname;
    private String address;
    private String postalcode;
    private String city;
    private String email;

    public User() {}

    public String getName() {
        return name;
    }

    public User setName(String name) {
        this.name = name;
        return this;
    }

    public String getFirstname() {
        return firstname;
    }

    public User setFirstname(String firstname) {
        this.firstname = firstname;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public User setAddress(String adress) {
        this.address = adress;
        return this;
    }

    public String getPostalcode() {
        return postalcode;
    }

    public User setPostalcode(String postalcode) {
        this.postalcode = postalcode;
        return this;
    }

    public String getCity() {
        return city;
    }

    public User setCity(String city) {
        this.city = city;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public User setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getObjectID() {
        return objectID;
    }

    public User setObjectID(String objectID) {
        this.objectID = objectID;
        return this;
    }
    
}
