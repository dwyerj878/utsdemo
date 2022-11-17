package net.dev.jcd.uts;

import java.util.LinkedHashSet;
import java.util.Set;

import javax.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;

import net.dev.jcd.uts.ws.TimeResource;
import net.dev.jcd.uts.ws.UserResource;
import net.dev.jcd.uts.ws.UtsResource;

@ApplicationScoped
@ApplicationPath("/rest")
public class UtsApplication extends Application {

	@Override
	public Set<Class<?>> getClasses() {
		Set<Class<?>> resources = new LinkedHashSet<Class<?>>();
		resources.add(UtsResource.class);
		resources.add(TimeResource.class);
		resources.add(UserResource.class);
		return resources;
	}

}