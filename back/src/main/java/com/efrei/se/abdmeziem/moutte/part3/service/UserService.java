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
public interface UserService {
    public Response addUser(String data);
    public Response deleteUser(String id);
    public Response getUser(String id);
    public Response getUsers();
    public Response updateUser(String data);
}
