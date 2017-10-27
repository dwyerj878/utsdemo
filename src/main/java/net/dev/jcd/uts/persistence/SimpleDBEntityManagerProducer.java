package net.dev.jcd.uts.persistence;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.transaction.TransactionManager;

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
    	LOG.info("Create");
        return Persistence
                .createEntityManagerFactory("SimpleDB")
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
