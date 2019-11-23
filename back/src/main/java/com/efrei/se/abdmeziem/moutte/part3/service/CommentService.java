/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.efrei.se.abdmeziem.moutte.part3.service;

import javax.ws.rs.core.Response;

/**
 *
 * @author hadri
 */
public interface CommentService {
    public Response addComment(String data);
    public Response deleteComment(String id);
    public Response getComment(String id);
    public Response getComments();
    public Response updateComment(String data);

}
