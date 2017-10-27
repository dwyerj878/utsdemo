package net.dev.jcd.uts;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import org.jboss.logging.Logger;

import net.dev.jcd.uts.persistence.UserDAO;

//@RequestScoped
@Named("SimpleProcessor")
public class SimpleProcessor {
	private static final Logger LOG = Logger.getLogger(SimpleProcessor.class);
	
	@Inject
	UserDAO dbSvc;

	
	/**
	 * Process Simple Request
	 * 
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public HashMap<String, Object> process(Map map) throws Exception {
		LOG.info("process :"+map);
		LOG.info("process :"+map.get("id") + map.get("name"));
		
		HashMap<String, Object> out = new HashMap<>();
		out.put("id", map.get("id"));
		out.put("status", "complete");
		try {
			dbSvc.save((int) map.get("id"),(String) map.get("name"));
		} catch (Exception ex) {
			LOG.error("Error", ex);
		}
		return out;
	}


}
