/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.efrei.se.abdmeziem.moutte.part3.service;

import com.algolia.search.DefaultSearchClient;
import com.algolia.search.SearchClient;
import com.algolia.search.SearchIndex;
import com.algolia.search.models.indexing.Query;
import com.algolia.search.models.indexing.SearchResult;
import com.efrei.se.abdmeziem.moutte.part3.model.Type;
import static com.efrei.se.abdmeziem.moutte.part3.utils.Constants.DB_ADMIN;
import static com.efrei.se.abdmeziem.moutte.part3.utils.Constants.DB_ADMIN_KEY;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

/**
 *
 * @author QuokkaKoala
 */
@Path("/type")
public class TypeServiceImpl implements TypeService {
    /**
    * The getQueryMap, parse Json to Map<String, String>
    * @return Map<String, String>
    */
    private static Map<String, String> getQueryMap(String query)
    {
        query = query.substring(1, query.length() - 1);
        String[] params = query.split(",");
        Map<String, String> map = new HashMap<>();
        for (String param : params)
        {
            String name = param.split(":")[0];
            name = name.substring(1, name.length() - 1);
            String value = param.split(":")[1];
            value = value.substring(1, value.length() - 1);
            map.put(name, value);
        }
        return map;
    }
    
    private SearchIndex<Type> connectionDB(){
        SearchClient client = DefaultSearchClient.create(DB_ADMIN, DB_ADMIN_KEY);
        SearchIndex<Type> index = client.initIndex("type", Type.class);
        return index;
    }
    
    /**
    * The addType, connect and add the type of media in the database 
    * The function verifies that the key corresponds to the object, use UUID to generate an ID for the media, save the type of database.
    * @return Response
    */
    
    @Override
    @POST
    @Consumes("application/json")
    @Produces("text/plain")
    public Response addType(String data) {
        Map<String, String> dataMap = TypeServiceImpl.getQueryMap(data);
        Type type = new Type();
        for (Map.Entry<String, String> entry : dataMap.entrySet()) {
            String key = entry.getKey();
            String val =  entry.getValue();
            if(!key.equals("name"))
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
            type.setName(val);
	}
        SearchIndex<Type> index = connectionDB();
        try {        
              UUID uuid = UUID.randomUUID();
              String randomUUIDString = uuid.toString();
              type.setObjectID(randomUUIDString);
              index.saveObject(type).waitTask();
            return Response.ok("Ok").build();
        } catch(Error error) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();  
        }
    }

    /**
    * The getAll, connect and get the data of all the type of media in the database 
    * The function makes a request without filter because we want all medias
    * @return Response
    */
    @Override
    @GET
    @Produces("application/json")
    public Response getTypes(){
        try {
        SearchIndex<Type> index = connectionDB();
        SearchResult<Type> allType = index.search(new Query());
        return Response.ok(allType).build();
        } catch(Error error) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    
    /**
    * The deleteType, connect and delete the type of media in the database 
    * The function delete the type of media with the id
    * @return Response
    */
    @Override
    @DELETE
    @Path("{objectID}")
    @Produces("text/plain")
    public Response deleteType(@PathParam("objectID") String id) {
        SearchIndex<Type> index = connectionDB();
        try{
           index.deleteObjectAsync(id);
           return Response
            .ok("ok")
            .build();
        }
        catch(Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
            .build();
        }
    }

    /**
    * The getType, connect and return the type of media in the database 
    * The function get the media with the id, there use a filter.
    * @return Response
    */
    @Override
    @GET
    @Path("{objectID}")
    @Consumes("application/json")
    @Produces("application/json")
    public Response getType(@PathParam("objectID") String id) {
        SearchIndex<Type> index = connectionDB();
        SearchResult<Type> type = index.search(new Query()
         .setFilters("objectID:'" + id + "'"));
        return Response.ok(type).build();
    }
    
    
    
    /**
    * The updateType, connect and change the data of type of media in the database 
    * The function retrieves the JSON, passes it in a map to process the data and update the Type of Media in the database
    * @return Response
    */
    @Override
    @PUT
    @Consumes("application/json")
    @Produces("text/plain")
    public Response updateType(String data) {
        Map<String, String> dataMap = TypeServiceImpl.getQueryMap(data);
        Type type = new Type();
        for (Map.Entry<String, String> entry : dataMap.entrySet()) {
            String key = entry.getKey();
            String val =  entry.getValue();
            switch(key) {
                case "name":
                    type.setName(val);
                    break;
                case "objectID":
                    type.setObjectID(val);
                    break;                
                default:
                    break;
                   
            }
	}
        SearchIndex<Type> index = connectionDB();
        try {        
            index.saveObject(type).waitTask();
            return Response.ok("Ok").build();
        } catch(Error error) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build(); 
        }
    }
   
}
