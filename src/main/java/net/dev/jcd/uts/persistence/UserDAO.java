package net.dev.jcd.uts.persistence;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

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
	public void save(int ID, String name) {
		LOG.info("save " + ID + " " + name);
		User u = new User();
		u.setID(ID);
		u.setName(name);
		em.persist(u);
		em.flush();
	}

	/**
	 * @return
	 */
	public List<User> getUsers() {
		TypedQuery<User> q = em.createQuery("select u from User u", User.class);
		List<User> r = q.getResultList();
		return r;
	}
}
