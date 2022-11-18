package net.dev.jcd.uts.ws;

import java.net.Inet4Address;
import java.net.UnknownHostException;

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
    	try {
			String message = "Hello from " + Inet4Address.getLocalHost().getHostAddress();
			return Response.ok(message).build();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
    	
        return Response.ok("Hello World").build();
    }
}