package net.dev.jcd.uts.ws;

import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import net.dev.jcd.uts.persistence.User;
import net.dev.jcd.uts.persistence.UserDAO;

@Path("/users")
public class UserResource {
	
	@Inject
	UserDAO userDao;

    @GET
    @RolesAllowed("USERS")
    @Produces(MediaType.APPLICATION_XML)
    public Response getUsers(){
//    public List<User> getUsers(){
        List<User> r = userDao.getUsers();
//        return r;
        GenericEntity<List<User>> entity = new GenericEntity<List<User>>(r) {};
        Response response = Response.ok(entity).build();
        return response;
    }
}