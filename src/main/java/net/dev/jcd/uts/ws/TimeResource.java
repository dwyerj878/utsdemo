package net.dev.jcd.uts.ws;

import javax.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import net.dev.jcd.uts.services.TimeService;

@Path("/time")
public class TimeResource {
	
	@Inject
	TimeService ts;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String sayHello(){
        return ts.getText();
    }
}