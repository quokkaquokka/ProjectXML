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
public interface MediaService {
    public Response addMedia(String data);
    public Response deleteMedia(String id);
    public Response getMedia(String id);
    public Response getMedias();
    public Response updateMedia(String data);
    public Response getSearch(String data);
    public Response getMediaByPublisher(String publi);
}
