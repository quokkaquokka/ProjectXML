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
import static com.efrei.se.abdmeziem.moutte.part3.utils.Constants.ALLOW_SITE;
import static com.efrei.se.abdmeziem.moutte.part3.utils.Constants.DB_ADMIN;
import static com.efrei.se.abdmeziem.moutte.part3.utils.Constants.DB_ADMIN_KEY;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.OPTIONS;
import javax.ws.rs.POST;
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
    private static Map<String, String> getQueryMap(String query)
    {
        query = query.substring(1, query.length() - 1);
        String[] params = query.split(",");
        Map<String, String> map = new HashMap<>();
        for (String param : params)
        {
            String name = param.split(":")[0];
            name = name.substring(1, name.length() - 1);
            String value = URLDecoder.decode(param.split(":")[1]);
            value = value.substring(1, value.length() - 1);
            map.put(name, value);
        }
        return map;
    }    
    
    /**
    * The addMedia, connect and add the data in the database 
    * The function retrieves the JSON, passes it in a map to process the data and adds it to the Media class
    * use UUID to generate an ID for the media.
    * @return Response
    */
    
    @Override
    // @OPTIONS
    @POST
    @Path("add")
    // application/x-www-form-urlencoded
    @Consumes("application/json")
    @Produces("text/plain")
    public Response addType(String data) {
        Map<String, String> dataMap = TypeServiceImpl.getQueryMap(data);
        Type type = new Type();
        for (Map.Entry<String, String> entry : dataMap.entrySet()) {
            String key = entry.getKey();
            String val =  entry.getValue();
            switch(key) {
                case "name":
                    type.setName(val);
                    break;
                default:
                    System.out.println(key + " not found in switch case!!!!");
            }
	}
       
        SearchClient client = DefaultSearchClient.create(DB_ADMIN, DB_ADMIN_KEY);
        SearchIndex<Type> index = client.initIndex("type", Type.class);
        try {        
              UUID uuid = UUID.randomUUID();
              String randomUUIDString = uuid.toString();
              type.setObjectID(randomUUIDString);
              index.saveObject(type).waitTask();
            return Response.ok("Ok")
              .header("Access-Control-Allow-Origin", ALLOW_SITE)
              .header("Access-Control-Allow-Methods", "POST, GET, PUT, UPDATE, OPTIONS")
              .header("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-With").build();
        } catch(Error error) {
            System.err.println(error);
            return Response.ok("kO")
              .header("Access-Control-Allow-Origin", ALLOW_SITE)
              .header("Access-Control-Allow-Methods", "POST, GET, PUT, UPDATE, OPTIONS")
              .header("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-With").build();
            
        }
    }

    /**
    * The getMedias, connect and get the data of all media in the database 
    * The function makes a request without filter because we want all medias
    * @return Response
    */
    @Override
    @GET
    @Path("getAll")
    @Produces("application/json")
    public Response getTypes(){
        SearchClient client = DefaultSearchClient.create(DB_ADMIN, DB_ADMIN_KEY);
        SearchIndex<Type> index = client.initIndex("type", Type.class);
        SearchResult<Type> allType = index.search(new Query());
        return Response.ok(allType)
          .header("Access-Control-Allow-Origin", ALLOW_SITE)
          .header("Access-Control-Allow-Methods", "POST, GET, PUT, UPDATE, OPTIONS")
          .header("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-With")
          .build();
    }
    
    
    /**
    * The deleteMedia, connect and delete the media in the database 
    * The function delete the media with the id
    * @return Response
    */
    @Override
    @DELETE
    @Path("delete/{objectID}")
    @Consumes("application/json")
    @Produces("text/plain")
    public Response deleteType(@PathParam("objectID") String id) {
        SearchClient client = DefaultSearchClient.create(DB_ADMIN, DB_ADMIN_KEY);
        SearchIndex<Type> index = client.initIndex("type", Type.class);
        
        try{
           index.deleteObject(id);
           return Response
            .ok("ok")
           .header("Access-Control-Allow-Origin", ALLOW_SITE)
           .header("Access-Control-Allow-Methods", "POST, GET, PUT, UPDATE, OPTIONS")
           .header("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-With")
           .build();
        }
        catch(Exception e) {
            System.out.print(e);
            return Response
            .ok("ko")
            .header("Access-Control-Allow-Origin", ALLOW_SITE)
            .header("Access-Control-Allow-Methods", "POST, GET, PUT, UPDATE, OPTIONS")
            .header("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-With")
            .build();
        }
    }

    /**
    * The getMedia, connect and return the media in the database 
    * The function get the media with the id, there use a filter.
    * @return Response
    */
    @Override
    @GET
    @Path("get/{objectID}")
    @Consumes("application/json")
    @Produces("application/json")
    public Response getType(@PathParam("objectID") String id) {
        id = id.substring(1, id.length() - 1);
        SearchClient client = DefaultSearchClient.create(DB_ADMIN, DB_ADMIN_KEY);
        SearchIndex<Type> index = client.initIndex("type", Type.class);
        SearchResult<Type> type = index.search(new Query()
         .setFilters("objectID:'" + id + "'"));
        return Response.ok(type)
            .header("Access-Control-Allow-Origin", ALLOW_SITE)
            .header("Access-Control-Allow-Methods", "POST, GET, PUT, UPDATE, OPTIONS")
            .header("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-With")
            .build();
    }
    
    
    
    /**
    * The updateMedia, connect and change the data of media in the database 
    * The function retrieves the JSON, passes it in a map to process the data and update the Media in the database
    * @return Response
    */
    @Override
    @POST
    @Path("update")
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
                    System.out.println(key + " not found in switch case!!!!");
            }
	}

        SearchClient client = DefaultSearchClient.create(DB_ADMIN, DB_ADMIN_KEY);
        SearchIndex<Type> index = client.initIndex("type", Type.class);
        try {        
            
            
            index.saveObject(type).waitTask();
            return Response.ok("Ok")
              .header("Access-Control-Allow-Origin", ALLOW_SITE)
              .header("Access-Control-Allow-Methods", "POST, GET, PUT, UPDATE, OPTIONS")
              .header("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-With")
              .build();
        } catch(Error error) {
            System.err.println(error);
            return Response.ok("kO")
              .header("Access-Control-Allow-Origin", ALLOW_SITE)
              .header("Access-Control-Allow-Methods", "POST, GET, PUT, UPDATE, OPTIONS")
              .header("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-With")
              .build(); 
        }
    }
   
}
