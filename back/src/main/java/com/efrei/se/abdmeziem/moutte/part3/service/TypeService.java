/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.efrei.se.abdmeziem.moutte.part3.service;

import javax.ws.rs.core.Response;

/**
 *
 * @author QuokkaKoala
 */
public interface TypeService {
    public Response addType(String data);
    public Response deleteType(String id);
    public Response getType(String id);
    public Response getTypes();
    public Response updateType(String data);
    
}
