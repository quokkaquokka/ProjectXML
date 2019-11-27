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
import com.efrei.se.abdmeziem.moutte.part3.model.Comment;
import com.efrei.se.abdmeziem.moutte.part3.model.Media;
import static com.efrei.se.abdmeziem.moutte.part3.utils.Constants.DB_ADMIN;
import static com.efrei.se.abdmeziem.moutte.part3.utils.Constants.DB_ADMIN_KEY;
import java.util.Arrays;
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
        String[] params = query.split("\",\"");
        Map<String, String> map = new HashMap<>();
        params[0] = params[0].substring(1, params[0].length());
        
        for (String param : params)
        {
            String[] keyValue = param.split("\":\"", 2);
            String name = keyValue[0];
            System.out.print(name);
            
            String value = keyValue[1];
            if(value.substring(value.length() -1, value.length()).equals("\""))
                value = value.substring(0, value.length() - 1);
            System.out.print(value);
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
            if(!keyw.equals(" "))
                media.addKeyWord(keyw);
        }
    }
    
    private SearchIndex<Media> connectionDB(){
        SearchClient client = DefaultSearchClient.create(DB_ADMIN, DB_ADMIN_KEY);
        SearchIndex<Media> index = client.initIndex("media", Media.class);
    return index;
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
                case "author":
                    media.setAuthor(val);
                    break;
                case "uid":
                    media.setUid(val);
                    break;
                case "type":
                    media.setType(val);
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
                    break;
            }
	} 
        try {        
              UUID uuid = UUID.randomUUID();
              String randomUUIDString = uuid.toString();
              media.setObjectID(randomUUIDString);
              SearchIndex<Media> index = connectionDB();
              index.saveObject(media).waitTask();
            return Response.ok("Ok").build();
        } catch(Error error) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
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
        SearchIndex<Media> index = connectionDB();
        SearchResult<Media> allMedia = index.search(new Query());
        return Response.ok(allMedia).build();
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
    public Response deleteMedia(@PathParam("objectID") String id) {
        SearchIndex<Media> index = connectionDB();
        try{
           index.deleteObjectAsync(id);
           SearchClient client = DefaultSearchClient.create(DB_ADMIN, DB_ADMIN_KEY);
           SearchIndex<Comment> indexComment = client.initIndex("comment", Comment.class);
           Query query = new Query().setFilters("mediaID:'" + id + "'");
           indexComment.deleteByAsync(query);
           return Response.ok("ok").build();
        }
        catch(Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
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
        SearchIndex<Media> index = connectionDB();
        SearchResult<Media> media = index.search(new Query()
         .setFilters("objectID:'" + id + "'"));
        return Response.ok(media).build();
    }
    
    
    
    /**
    * The updateMedia, connect and change the data of media in the database 
    * The function retrieves the JSON, passes it in a map to process the data and update the Media in the database
    * @return Response
    */
    @Override
    @PUT
    @Path("update")
    @Consumes("application/json")
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
                case "type":
                    media.setType(val);
                    break;
                case "date":
                    media.setDate(val);
                    break;
                case "keyWords":
                    splitKW(media, val);
                    break;
                default:
                    break;
            }
	}

        SearchIndex<Media> index = connectionDB();
        try {        
            index.saveObject(media).waitTask();
            return Response.ok("Ok").build();
        } catch(Error error) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build(); 
        }
    }
    
    /**
    * The getSearch, connect and get the data of the database corresponding to the result of the search.
    * The function make a request to the database.
    * @return Response
    */
    @Override
    @GET
    @Path("search/{keyword}")
    @Consumes("application/json")
    @Produces("application/json")
    public Response getSearch(@PathParam("keyword") String search){
        try{
        SearchIndex<Media> index = connectionDB();
        SearchResult<Media> media = index.search(new Query(search)
                .setAttributesToRetrieve(Arrays.asList("name","author","type","keyWords","icon")));
        return Response.ok(media).build();
        } catch(Error error){
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }
    
}
