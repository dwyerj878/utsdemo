package net.dev.jcd.uts.ws;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/uts")
public class UtsResource {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String sayHello(){
        return "Hello World";
    }
}