package com.efrei.se.abdmeziem.moutte.part3.service;

import com.algolia.search.DefaultSearchClient;
import com.efrei.se.abdmeziem.moutte.part3.model.Employees;
import com.algolia.search.SearchClient;
import com.algolia.search.SearchIndex;
import com.algolia.search.models.indexing.Query;
import com.algolia.search.models.indexing.SearchResult;
import static com.efrei.se.abdmeziem.moutte.part3.utils.Constants.*;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.UUID;
/**
 *
 * @author Camille Moutte and Theo Abdmeziem
 */
@Path("/employees")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class EmployeesServiceImpl implements EmployeesService {
    
    /**
    * The addEmployees, connect and add the data in the database 
    * The function retrieves the JSON, passes it in a map to process the data and adds it to the Employees class
    * use UUID to generate an ID for the user.
    * @return Response
    */
    
    @Override
    @POST
    @Path("add")
    @Consumes("application/x-www-form-urlencoded")
    @Produces("text/plain")
    public Response addEmployees(String data) {
        Map<String, String> dataMap = EmployeesServiceImpl.getQueryMap(data);
        Employees employees = new Employees();
        String key;
        String val;
        for (Map.Entry<String, String> entry : dataMap.entrySet()) {
            key = entry.getKey();
            val =  entry.getValue();
            switch(key) {
                case "name":
                    employees.setName(val);
                    break;                
                case "adress":
                    employees.setAdress(val);
                    break;
                case "city":
                    employees.setCity(val);
                    break;
                case "email":
                    employees.setEmail(val);
                    break;
                case "firstname":
                    employees.setFirstname(val);
                    break;
                case "postalcode":
                    employees.setPostalcode(val);
                    break;
                case "telhome":
                    employees.setTelhome(val);
                    break;
                case "telmob":
                    employees.setTelmob(val);
                    break;
                case "telpro":
                    employees.setTelpro(val);
                    break;
                
                default:
                    System.out.println(key + "Not found in switch case !!!!");
            }
	}
       
        SearchClient client = DefaultSearchClient.create(DB_ADMIN, DB_ADMIN_KEY);
        SearchIndex<Employees> index = client.initIndex("employees", Employees.class);
        try {        
              UUID uuid = UUID.randomUUID();
              String randomUUIDString = uuid.toString();
              employees.setObjectID(randomUUIDString);
              index.saveObject(employees).waitTask();
            return Response.ok("Ok").build();
        } catch(Error error) {
            System.err.println(error);
            return Response.ok("kO").build();
            
        }
    }

    /**
    * The getAllEmployees, connect and get the data of all Employees in the database 
    * The function makes a request without filter because we want all employees
    * @return Response
    */
    @Override
    @GET
    @Path("getAll")
    @Produces("application/json")
    public Response getAllEmployees(){
        SearchClient client = DefaultSearchClient.create(DB_ADMIN, DB_ADMIN_KEY);
        SearchIndex<Employees> index = client.initIndex("employees", Employees.class);
        SearchResult<Employees> allEmployees = index.search(new Query());
        return Response.ok(allEmployees).header("Access-Control-Allow-Origin", "http://localhost:8282")
      .header("Access-Control-Allow-Methods", "POST, GET, PUT, UPDATE, OPTIONS")
      .header("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-With").build();
    }
    
    
    /**
    * The deleteEmployees, connect and delete the employees in the database 
    * The function delete the employees with the id
    * @return Response
    */
    @Override
    @GET
    @Path("delete/{objectID}")
    @Consumes("application/json")
    @Produces("text/plain")
    public Response deleteEmployees(@PathParam("objectID") String id) {
        id = id.substring(1, id.length() - 1);
        SearchClient client = DefaultSearchClient.create(DB_ADMIN, DB_ADMIN_KEY);
        SearchIndex<Employees> index = client.initIndex("employees", Employees.class);
        
        try{
           index.deleteObject(id);
           return Response
            .ok("ok")
            .build();
        }
        catch(Exception e) {
            System.out.print(e);
            return Response
            .ok("ko")
            .build();
        }
    }

    /**
    * The getEmployees, connect and return the employees in the database 
    * The function get the employees with the id, there use a filter.
    * @return Response
    */
    @Override
    @GET
    @Path("get/{objectID}")
    @Consumes("application/json")
    @Produces("application/json")
    public Response getEmployees(@PathParam("objectID") String id) {
        id = id.substring(1, id.length() - 1);
        SearchClient client = DefaultSearchClient.create(DB_ADMIN, DB_ADMIN_KEY);
        SearchIndex<Employees> index = client.initIndex("employees", Employees.class);
        SearchResult<Employees> employees = index.search(new Query()
         .setFilters("objectID:'" + id + "'"));
        // return Response.ok(employees).build();
        
        return Response.ok(employees).header("Access-Control-Allow-Origin", "http://localhost:8282")
      .header("Access-Control-Allow-Methods", "POST, GET, PUT, UPDATE, OPTIONS")
      .header("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-With").build();
    }
    
    private static Map<String, String> getQueryMap(String query)
    {
        String[] params = query.split("&");
        Map<String, String> map = new HashMap<>();
        for (String param : params)
        {
            String name = param.split("=")[0];
            String value = URLDecoder.decode(param.split("=")[1]);
            map.put(name, value);
        }
        return map;
    }
    
    
    /**
    * The updateEmployees, connect and change the data of employees in the database 
    * The function retrieves the JSON, passes it in a map to process the data and update the Employees in the database
    * @return Response
    */
    @Override
    @POST
    @Path("update")
    @Consumes("application/x-www-form-urlencoded")
    @Produces("text/plain")
    public Response updateEmployees(String data) {
        Map<String, String> dataMap = EmployeesServiceImpl.getQueryMap(data);
        Employees employees = new Employees();
  
        for (Map.Entry<String, String> entry : dataMap.entrySet()) {
            String key = entry.getKey();
            String val =  entry.getValue();
            switch(key) {
                case "name":
                    employees.setName(val);
                    break;
                case "objectID":
                    employees.setObjectID(val);
                    break;                
                case "adress":
                    employees.setAdress(val);
                    break;
                case "city":
                    employees.setCity(val);
                    break;
                case "email":
                    employees.setEmail(val);
                    break;
                case "firstname":
                    employees.setFirstname(val);
                    break;
                case "postalcode":
                    employees.setPostalcode(val);
                    break;
                case "telhome":
                    employees.setTelhome(val);
                    break;
                case "telmob":
                    employees.setTelmob(val);
                    break;
                case "telpro":
                    employees.setTelpro(val);
                    break;
                
                default:
                    System.out.println(key + " not found in switch case!!!!");
            }
	}

        SearchClient client = DefaultSearchClient.create(DB_ADMIN, DB_ADMIN_KEY);
        SearchIndex<Employees> index = client.initIndex("employees", Employees.class);
        try {        
            index.saveObject(employees).waitTask();
            return Response.ok("Ok").build();
        } catch(Error error) {
            System.err.println(error);
            return Response.ok("kO").build(); 
        }
    }
}
