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
 * @author hadri
 */

@Path("/comment")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class CommentServiceImpl implements CommentService {

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
    
    @Override
    @POST
    @Path("add")
    @Consumes("application/json")
    @Produces("text/plain")
    public Response addComment(String data) {
        Map<String, String> dataMap = CommentServiceImpl.getQueryMap(data);
        Comment comment = new Comment();
        for (Map.Entry<String, String> entry : dataMap.entrySet()) {
            String key = entry.getKey();
            String val =  entry.getValue();
            switch(key) {
                case "publisherID":
                    comment.setPublisherID(val);
                    break;               
                case "mediaID":
                    comment.setMediaID(val);
                    break;
                case "text":
                    comment.setText(val);
                    break;
                case "grade":
                    comment.setGrade(val);
                    break;
                default:
                    System.out.println(key + " not found in switch case!!!!");
            }
	}
       
        SearchClient client = DefaultSearchClient.create(DB_ADMIN, DB_ADMIN_KEY);
        SearchIndex<Comment> index = client.initIndex("comment", Comment.class);
        try {        
              UUID uuid = UUID.randomUUID();
              String randomUUIDString = uuid.toString();
              comment.setObjectID(randomUUIDString);
              index.saveObject(comment).waitTask();
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

    @Override
    @GET
    @Path("delete/{objectID}")
    @Consumes("application/json")
    @Produces("text/plain")
    public Response deleteComment(@PathParam("objectID") String id) {
        id = id.substring(1, id.length() - 1);
        SearchClient client = DefaultSearchClient.create(DB_ADMIN, DB_ADMIN_KEY);
        SearchIndex<Comment> index = client.initIndex("comment", Comment.class);
        
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

    @Override
    @GET
    @Path("get/{objectID}")
    @Consumes("application/json")
    @Produces("application/json")
    public Response getComment(@PathParam("objectID") String id) {
        System.out.print(id);
        SearchClient client = DefaultSearchClient.create(DB_ADMIN, DB_ADMIN_KEY);
        SearchIndex<Comment> index = client.initIndex("comment", Comment.class);
        SearchResult<Comment> user = index.search(new Query()
         .setFilters("mediaID:'" + id + "'"));
        return Response.ok(user)
            .header("Access-Control-Allow-Origin", ALLOW_SITE)
            .header("Access-Control-Allow-Methods", "POST, GET, PUT, UPDATE, OPTIONS")
            .header("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-With")
            .build();
    }

    @Override
    @GET
    @Path("getAll")
    @Produces("application/json")
    public Response getComments() {
        SearchClient client = DefaultSearchClient.create(DB_ADMIN, DB_ADMIN_KEY);
        SearchIndex<Comment> index = client.initIndex("comment", Comment.class);
        SearchResult<Comment> allComment = index.search(new Query());
        return Response.ok(allComment)
          .header("Access-Control-Allow-Origin", ALLOW_SITE)
          .header("Access-Control-Allow-Methods", "POST, GET, PUT, UPDATE, OPTIONS")
          .header("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-With")
          .build();
    }

    @Override
    @POST
    @Path("update")
    @Consumes("application/x-www-form-urlencoded")
    @Produces("text/plain")
    public Response updateComment(String data) {
        Map<String, String> dataMap = CommentServiceImpl.getQueryMap(data);
        Comment comment = new Comment();
  
        for (Map.Entry<String, String> entry : dataMap.entrySet()) {
            String key = entry.getKey();
            String val =  entry.getValue();
            switch(key) {
                case "publisherID":
                    comment.setPublisherID(val);
                    break;               
                case "mediaID":
                    comment.setMediaID(val);
                    break;
                case "text":
                    comment.setText(val);
                    break;
                case "grade":
                    comment.setGrade(val);
                    break;
                case "objectID":
                    comment.setObjectID(val);
                    break;
                default:
                    System.out.println(key + " not found in switch case!!!!");
            }
	}

        SearchClient client = DefaultSearchClient.create(DB_ADMIN, DB_ADMIN_KEY);
        SearchIndex<Comment> index = client.initIndex("comment", Comment.class);
        try {        
            index.saveObject(comment).waitTask();
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
