/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.efrei.se.abdmeziem.moutte.part3.model;

import java.util.ArrayList;

/**
 *
 * @author QuokkaKoala
 */
public class Media {
    private String objectID;
    private String name;
    private String author;
    private String icon;
    private String date;
    private String uid;
    private String type;
    private ArrayList<String> keyWords;
    
    public Media() {}

    public String getName() {
        return name;
    }

    public Media setName(String name) {
        this.name = name;
        return this;
    }
    
    public String getIcon() {
        return icon;
    }

    public Media setIcon(String icone) {
        this.icon = icone;
        return this;
    }

    public String getAuthor() {
        return author;
    }

    public Media setAuthor(String author) {
        this.author = author;
        return this;
    }

    public String getType() {
        return type;
    }

    public Media setType(String type) {
        this.type = type;
        return this;
    }
    public String getDate() {
        return date;
    }

    public Media setDate(String date) {
        this.date = date;
        return this;
    }

    public String getUid() {
        return uid;
    }

    public Media setUid(String uid) {
        this.uid = uid;
        return this;
    }

    public ArrayList<String> getKeyWords() {
        return keyWords;
    }
    
    public void addKeyWord(String keyword) {
        this.keyWords.add(keyword);
    }

    public Media setKeyWords(ArrayList<String> keyWords) {
        this.keyWords = keyWords;
        return this;
    }

    public String getObjectID() {
        return objectID;
    }

    public Media setObjectID(String objectID) {
        this.objectID = objectID;
        return this;
    }
    
    
}
