package net.dev.jcd.uts.ws;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

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