package com.efrei.se.abdmeziem.moutte.part3.service;

import com.algolia.search.DefaultSearchClient;
import com.algolia.search.SearchClient;
import com.algolia.search.SearchIndex;
import com.algolia.search.models.indexing.Query;
import com.algolia.search.models.indexing.SearchResult;
import com.efrei.se.abdmeziem.moutte.part3.model.User;
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

@Path("/user")
//@Consumes(MediaType.APPLICATION_JSON)
// @Produces(MediaType.APPLICATION_JSON)
public class UserServiceImpl implements UserService{
    
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
    * The addUser, connect and add the data in the database 
    * The function retrieves the JSON, passes it in a map to process the data and adds it to the User class
    * use UUID to generate an ID for the user.
    * @return Response
    */
    
    @Override
    @POST
    @Path("add")
    @Consumes("application/x-www-form-urlencoded")
    @Produces("text/plain")
    public Response addUser(String data) {
        Map<String, String> dataMap = UserServiceImpl.getQueryMap(data);
        User user = new User();
        for (Map.Entry<String, String> entry : dataMap.entrySet()) {
            String key = entry.getKey();
            String val =  entry.getValue();
            switch(key) {
                case "name":
                    user.setName(val);
                    break;                
                case "adress":
                    user.setAddress(val);
                    break;
                case "city":
                    user.setCity(val);
                    break;
                case "email":
                    user.setEmail(val);
                    break;
                case "firstname":
                    user.setFirstname(val);
                    break;
                case "postalcode":
                    user.setPostalcode(val);
                    break;
                default:
                    System.out.println(key + "Not found in switch case !!!!");
            }
	}
       
        SearchClient client = DefaultSearchClient.create(DB_ADMIN, DB_ADMIN_KEY);
        SearchIndex<User> index = client.initIndex("user", User.class);
        try {        
              UUID uuid = UUID.randomUUID();
              String randomUUIDString = uuid.toString();
              user.setObjectID(randomUUIDString);
              index.saveObject(user).waitTask();
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
    * The getUsers, connect and get the data of all user in the database 
    * The function makes a request without filter because we want all users
    * @return Response
    */
    @Override
    @GET
    @Path("getAll")
    @Produces("application/json")
    public Response getUsers(){
        SearchClient client = DefaultSearchClient.create(DB_ADMIN, DB_ADMIN_KEY);
        SearchIndex<User> index = client.initIndex("user", User.class);
        SearchResult<User> allUser = index.search(new Query());
        return Response.ok(allUser)
          .header("Access-Control-Allow-Origin", ALLOW_SITE)
          .header("Access-Control-Allow-Methods", "POST, GET, PUT, UPDATE, OPTIONS")
          .header("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-With")
          .build();
    }
    
    
    /**
    * The deleteUser, connect and delete the user in the database 
    * The function delete the user with the id
    * @return Response
    */
    @Override
    @GET
    @Path("delete/{objectID}")
    @Consumes("application/json")
    @Produces("text/plain")
    public Response deleteUser(@PathParam("objectID") String id) {
        id = id.substring(1, id.length() - 1);
        SearchClient client = DefaultSearchClient.create(DB_ADMIN, DB_ADMIN_KEY);
        SearchIndex<User> index = client.initIndex("user", User.class);
        
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
    * The getUser, connect and return the user in the database 
    * The function get the user with the id, there use a filter.
    * @return Response
    */
    @Override
    @GET
    @Path("get/{objectID}")
    @Consumes("application/json")
    @Produces("application/json")
    public Response getUser(@PathParam("objectID") String id) {
        SearchClient client = DefaultSearchClient.create(DB_ADMIN, DB_ADMIN_KEY);
        SearchIndex<User> index = client.initIndex("user", User.class);
        SearchResult<User> user = index.search(new Query()
         .setFilters("objectID:'" + id + "'"));
        return Response.ok(user)
            .header("Access-Control-Allow-Origin", ALLOW_SITE)
            .header("Access-Control-Allow-Methods", "POST, GET, PUT, UPDATE, OPTIONS")
            .header("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-With")
            .build();
    }
    
    
    
    /**
    * The updateUser, connect and change the data of user in the database 
    * The function retrieves the JSON, passes it in a map to process the data and update the User in the database
    * @return Response
    */
    @Override
    @POST
    @Path("update")
    @Consumes("application/json")
    @Produces("text/plain")
    public Response updateUser(String data) {
        Map<String, String> dataMap = UserServiceImpl.getQueryMap(data);
        User user = new User();
  
        for (Map.Entry<String, String> entry : dataMap.entrySet()) {
            String key = entry.getKey();
            String val =  entry.getValue();
            switch(key) {
                case "name":
                    user.setName(val);
                    break;
                case "objectID":
                    user.setObjectID(val);
                    break;                
                case "address":
                    user.setAddress(val);
                    break;
                case "city":
                    user.setCity(val);
                    break;
                case "email":
                    user.setEmail(val);
                    break;
                case "firstname":
                    user.setFirstname(val);
                    break;
                case "postalcode":
                    user.setPostalcode(val);
                    break;
                default:
                    System.out.println(key + " not found in switch case!!!!");
            }
	}

        SearchClient client = DefaultSearchClient.create(DB_ADMIN, DB_ADMIN_KEY);
        SearchIndex<User> index = client.initIndex("user", User.class);
        try {        
            index.saveObject(user).waitTask();
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
