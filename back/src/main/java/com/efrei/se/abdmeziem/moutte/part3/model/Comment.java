/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.efrei.se.abdmeziem.moutte.part3.model;

/**
 *
 * @author hadri
 */
public class Comment {
    private String objectID;
    private String publisherID;
    private String mediaID;
    private String text;
    private String grade;
    private String publisherName;
    
    public Comment() {}
    
    public String getPublisherName() {
        return publisherName;
    }
    
    public void setPublisherName(String uname) {
        publisherName = uname;
    }
    
    public String getObjectID() {
        return objectID;
    }
    
    public void setObjectID(String objectID) {
        this.objectID = objectID;
    }
    
    public String getPublisherID() {
        return publisherID;
    }
    
    public void setPublisherID(String publisherID) {
        this.publisherID = publisherID;
    }
    
    public String getMediaID() {
        return mediaID;
    }
    
    public void setMediaID(String mediaID) {
        this.mediaID = mediaID;
    }
    
    public String getText() {
        return text;
    }
    
    public void setText(String text) {
        this.text = text;
    }
    
    public String getGrade() {
        return grade;
    }
    
    public void setGrade(String grade) {
        this.grade = grade;
    }
}
