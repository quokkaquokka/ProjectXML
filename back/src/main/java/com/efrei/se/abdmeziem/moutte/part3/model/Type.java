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
public class Type {
    
    private String objectID;
    private String name;

    public Type() {}

    public String getName() {
        return name;
    }

    public Type setName(String name) {
        this.name = name;
        return this;
    }

    public String getObjectID() {
        return objectID;
    }

    public Type setObjectID(String objectID) {
        this.objectID = objectID;
        return this;
    }
    
}
