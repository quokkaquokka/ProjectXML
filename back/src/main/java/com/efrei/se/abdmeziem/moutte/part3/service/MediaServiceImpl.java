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
import com.efrei.se.abdmeziem.moutte.part3.model.Media;
import static com.efrei.se.abdmeziem.moutte.part3.utils.Constants.ALLOW_SITE;
import static com.efrei.se.abdmeziem.moutte.part3.utils.Constants.DB_ADMIN;
import static com.efrei.se.abdmeziem.moutte.part3.utils.Constants.DB_ADMIN_KEY;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author QuokkaKoala
 */

@Path("/media")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class MediaServiceImpl implements MediaService {
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
    
    private void splitKW(Media media, String kw) {
        String[] keywords = kw.split(",");
        for (String keyw : keywords) {
            media.addKeyWord(keyw);
        }
    }
    
    @Override
    @POST
    @Path("add")
    @Consumes("application/json")
    @Produces("text/plain")
    public Response addMedia(String data) {
        Map<String, String> dataMap = MediaServiceImpl.getQueryMap(data);
        Media media = new Media();
        for (Map.Entry<String, String> entry : dataMap.entrySet()) {
            String key = entry.getKey();
            String val =  entry.getValue();
            switch(key) {
                case "name":
                    media.setName(val);
                    break;
                /*case "objectID":
                    media.setObjectID(val);
                    break;*/                
                case "author":
                    media.setAuthor(val);
                    break;
                case "uid":
                    media.setUid(val);
                    break;
                case "icon":
                    media.setIcon(val);
                    break;
                case "date":
                    media.setDate(val);
                    break;
                case "keyWords":
                    splitKW(media, val);
                    break;
                default:
                    System.out.println(key + " not found in switch case!!!!");
            }
	}
       
        SearchClient client = DefaultSearchClient.create(DB_ADMIN, DB_ADMIN_KEY);
        SearchIndex<Media> index = client.initIndex("media", Media.class);
        try {        
              UUID uuid = UUID.randomUUID();
              String randomUUIDString = uuid.toString();
              media.setObjectID(randomUUIDString);
              index.saveObject(media).waitTask();
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
    public Response getMedias(){
        SearchClient client = DefaultSearchClient.create(DB_ADMIN, DB_ADMIN_KEY);
        SearchIndex<Media> index = client.initIndex("media", Media.class);
        SearchResult<Media> allMedia = index.search(new Query());
        return Response.ok(allMedia)
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
    @GET
    @Path("delete/{objectID}")
    @Consumes("application/json")
    @Produces("text/plain")
    public Response deleteMedia(@PathParam("objectID") String id) {
        id = id.substring(1, id.length() - 1);
        SearchClient client = DefaultSearchClient.create(DB_ADMIN, DB_ADMIN_KEY);
        SearchIndex<Media> index = client.initIndex("media", Media.class);
        
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
    public Response getMedia(@PathParam("objectID") String id) {
        SearchClient client = DefaultSearchClient.create(DB_ADMIN, DB_ADMIN_KEY);
        SearchIndex<Media> index = client.initIndex("media", Media.class);
        SearchResult<Media> media = index.search(new Query()
         .setFilters("objectID:'" + id + "'"));
        return Response.ok(media)
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
    @Consumes("application/x-www-form-urlencoded")
    @Produces("text/plain")
    public Response updateMedia(String data) {
        Map<String, String> dataMap = MediaServiceImpl.getQueryMap(data);
        Media media = new Media();
  
        for (Map.Entry<String, String> entry : dataMap.entrySet()) {
            String key = entry.getKey();
            String val =  entry.getValue();
            switch(key) {
                case "name":
                    media.setName(val);
                    break;
                case "objectID":
                    media.setObjectID(val);
                    break;                
                case "author":
                    media.setAuthor(val);
                    break;
                case "uid":
                    media.setUid(val);
                    break;
                case "date":
                    media.setDate(val);
                    break;
                case "keyWords":
                    splitKW(media, val);
                    break;
                default:
                    System.out.println(key + " not found in switch case!!!!");
            }
	}

        SearchClient client = DefaultSearchClient.create(DB_ADMIN, DB_ADMIN_KEY);
        SearchIndex<Media> index = client.initIndex("media", Media.class);
        try {        
            index.saveObject(media).waitTask();
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
