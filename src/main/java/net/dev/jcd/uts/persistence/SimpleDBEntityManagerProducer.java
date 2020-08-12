package net.dev.jcd.uts.persistence;

import java.util.HashMap;
import java.util.Map;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.transaction.TransactionManager;

import org.apache.deltaspike.core.api.config.ConfigProperty;
import org.apache.deltaspike.core.api.config.ConfigResolver;
import org.jboss.logging.Logger;


/**
 * 
 * Create SimpleDB Entity Manager
 * 
 * @author jcdwyer
 *
 */
@ApplicationScoped
public class SimpleDBEntityManagerProducer {
	private static final Logger LOG = Logger.getLogger(SimpleDBEntityManagerProducer.class);
    @Produces
    @SimpleDB
    private EntityManager  createEM() {
    	LOG.info("Create SimpleDB EntityManager");
    	
        Map<String, String> allProperties = ConfigResolver.getAllProperties();
        Map<String, String> override = new HashMap<String, String>();
        for (Map.Entry<String, String> e : allProperties.entrySet()) 
        	if (e.getKey().startsWith("simpleDB."))
        		override.put(e.getKey().substring(9), e.getValue());
        
		return Persistence
                .createEntityManagerFactory("SimpleDB", override)
                .createEntityManager();
    }
	
    public void close(
            @Disposes @SimpleDB EntityManager entityManager) {
    	LOG.info("close");
        entityManager.close();
    }
    
//    @Produces
//    TransactionManager createTM() {
//    	tm = new HibernateTransactionManager();
//    }
}
