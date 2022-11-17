package net.dev.jcd.uts.persistence;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import jakarta.transaction.Transactional;

import org.jboss.logging.Logger;

/**
 * @author jcdwyer
 *
 */
public class UserDAO {
	private static final Logger LOG = Logger.getLogger(UserDAO.class);
	@Inject
	@SimpleDB
	EntityManager em;

	@PostConstruct
	public void setup() {
		LOG.info("Setup : " + em);
	}

	/**
	 * @param ID
	 * @param name
	 */
	@Transactional(Transactional.TxType.REQUIRED)
	public void save(int ID, String name) {
		LOG.info("save " + ID + " " + name);
		em.getTransaction().begin();
		User u = new User();
		u.setID(ID);
		u.setName(name);
		em.persist(u);
		
//		em.flush();
		em.getTransaction().commit();
		
	}

	/**
	 * @return
	 */
	public List<User> getUsers() {
		LOG.debug("Find users");
		TypedQuery<User> q = em.createQuery("select u from User u", User.class);
		List<User> r = q.getResultList();
		LOG.debug("found "+r.size()+"users");
		return r;
	}
}
