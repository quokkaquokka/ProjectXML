package com.efrei.se.abdmeziem.moutte.part3.service;

import com.algolia.search.DefaultSearchClient;
import com.algolia.search.SearchClient;
import com.algolia.search.SearchIndex;
import com.algolia.search.models.indexing.Query;
import com.algolia.search.models.indexing.SearchResult;
import com.efrei.se.abdmeziem.moutte.part3.model.User;
import static com.efrei.se.abdmeziem.moutte.part3.utils.Constants.DB_ADMIN;
import static com.efrei.se.abdmeziem.moutte.part3.utils.Constants.DB_ADMIN_KEY;
import java.net.URLDecoder;
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

        if(query == null)
            return null;
        Map<String, String> map = new HashMap<>();
        query = query.substring(1, query.length() - 1);
        String[] params = query.split(",");
        for (String param : params)
        {
            String name = param.split(":")[0];
            name = name.substring(1, name.length() - 1);
            System.out.print("name " + name);
            String value = URLDecoder.decode(param.split(":")[1]);
            value = value.substring(1, value.length() - 1);
            System.out.print("value " + value);
            map.put(name, value);
        }
        return map;
    }
    
    
    private SearchIndex<User> connectionDB(){
        SearchClient client = DefaultSearchClient.create(DB_ADMIN, DB_ADMIN_KEY);
        SearchIndex<User> index = client.initIndex("user", User.class);
        return index;
    }
    
    /**
    * The addUser, connect and add the data in the database 
    * The function retrieves the JSON, passes it in a map to process the data and adds it to the User class
    * use UUID to generate an ID for the user.
    * @return Response
    */
    
    @Override
    @POST
    @Consumes("application/json")
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
                case "password":
                    user.setPassword(val);
                    break;
                case "firstname":
                    user.setFirstname(val);
                    break;
                case "postalcode":
                    user.setPostalcode(val);
                    break;
                default:
                    break;
            }
	}
       
        SearchClient client = DefaultSearchClient.create(DB_ADMIN, DB_ADMIN_KEY);
        SearchIndex<User> index = client.initIndex("user", User.class);
        try {        
            UUID uuid = UUID.randomUUID();
            String randomUUIDString = uuid.toString();
            user.setObjectID(randomUUIDString);
            index.saveObject(user).waitTask();
            return Response.ok("Ok").build();
        } catch(Error error) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build(); 
        }
    }

    /**
    * The getUsers, connect and get the data of all user in the database 
    * The function makes a request without filter because we want all users
    * @return Response
    */
    @Override
    @GET
    @Produces("application/json")
    public Response getUsers(){
        try{
        SearchIndex<User> index = connectionDB();
        SearchResult<User> allUser = index.search(new Query());
        return Response.ok(allUser).build();
        } catch(Error error) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build(); 
        }
    }
    
    
    /**
    * The deleteUser, connect and delete the user in the database 
    * The function delete the user with the id
    * @return Response
    */
    @Override
    @DELETE
    @Path("{objectID}")
    @Produces("text/plain")
    public Response deleteUser(@PathParam("objectID") String id) {
        try{
           SearchIndex<User> index = connectionDB();
           index.deleteObject(id);
           return Response
            .ok("ok").build();
        } catch(Error error) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build(); 
        }
    }

    /**
    * The getUser, connect and return the user in the database 
    * The function get the user with the id, there use a filter.
    * @return Response
    */
    @Override
    @GET
    @Path("{objectID}")
    @Produces("application/json")
    public Response getUser(@PathParam("objectID") String id) {
        try{
        SearchIndex<User> index = connectionDB();
        SearchResult<User> user = index.search(new Query()
         .setFilters("objectID:'" + id + "'"));
        return Response.ok(user).build();
        } catch(Error error) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build(); 
        }
    }
    
    
    
    /**
    * The updateUser, connect and change the data of user in the database 
    * The function retrieves the JSON, passes it in a map to process the data and update the User in the database
    * @return Response
    */
    @Override
    @PUT
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
                     break;
            }
	}
        try {
            SearchIndex<User> index = connectionDB();
            index.saveObject(user).waitTask();
            return Response.ok("Ok").build();
        } catch(Error error) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build(); 
        }
    }
    
    
    /**
    * The getUser by email and pasword, connect and return the user in the database 
    * The function get the user with the email and password, there use a filter.
    * @return Response
    */
    @Override
    @POST
    @Path("authentification")
    @Consumes("application/json")
    @Produces("application/json")
    public Response getUserEmailPassword(String data) {
        Map<String, String> dataMap = UserServiceImpl.getQueryMap(data);
        String email = dataMap.get("email");
        String password = dataMap.get("password");
        System.out.print("email " + email + " password " + password);
        
        SearchIndex<User> index = connectionDB();
        SearchResult<User> user = index.search(new Query()
         .setFilters("email:" + email + " AND password:" + password)
         .setFilters("password:'" + password + "'")
        );
        return Response.ok(user).build();
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
        SearchIndex<User> index = connectionDB();
        SearchResult<User> media = index.search(new Query(search)
                .setAttributesToRetrieve(Arrays.asList("name","city","postalcode","email","address","firstname")));
        return Response.ok(media).build();
        } catch(Error error){
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

}
