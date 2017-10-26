package net.dev.jcd.uts;

import java.util.Date;

import javax.enterprise.context.RequestScoped;

import org.jboss.logging.Logger;

@RequestScoped
public class TimeService {
	private static final Logger LOG = Logger.getLogger(TimeService.class);
	public String getText() {
		LOG.debug("get Time");
		return String.format("Time %d", new Date().getTime());
	}

}
