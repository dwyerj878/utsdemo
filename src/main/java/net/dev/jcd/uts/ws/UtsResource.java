package net.dev.jcd.uts.ws;

import java.net.Inet4Address;
import java.net.UnknownHostException;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import net.dev.jcd.uts.ws.model.Uts;

@Path("/")
public class UtsResource {

    @GET
    @Path("/uts")
    @Produces(MediaType.APPLICATION_JSON)
    public Response sayHello(){
    	try {
			String message = "Hello from " + Inet4Address.getLocalHost().getHostAddress();
			return Response.ok(new Uts(message)).build();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
    	
        return Response.ok(new Uts("Hello World")).build();
    }
}