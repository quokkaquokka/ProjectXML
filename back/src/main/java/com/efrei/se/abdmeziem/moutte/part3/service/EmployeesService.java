/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.efrei.se.abdmeziem.moutte.part3.service;
import javax.ws.rs.core.Response;

/**
 *
 * @author Camille Moutte
 */

public interface EmployeesService {
    public Response addEmployees(String data);
    public Response deleteEmployees(String data);
    public Response getEmployees(String id);
    public Response getAllEmployees();
    public Response updateEmployees(String employeeID);
    
   
    
    
    
}
