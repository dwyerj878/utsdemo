package net.dev.jcd.uts;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

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