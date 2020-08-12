package net.dev.jcd.uts.ws;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/")
public class UtsResource {

    @GET
    @Path("/uts")
    @Produces(MediaType.TEXT_PLAIN)
    public Response sayHello(){
    	System.out.println("Hello World");
        return Response.ok("Hello World").build();
    }
}