package pl.pap.utils;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.Query;

import pl.pap.model.User;

public class SessionGuardian {

	// Entity manager

	EntityManagerFactory entityManagerFactory = Persistence
			.createEntityManagerFactory("PAPserver");
	EntityManager entityManager = entityManagerFactory.createEntityManager();

	public boolean checkSession(String login, String id) {

		User user = null;
		if (Utility.isNotNull(login) && Utility.isNotNull(id)) {

			// entityManager.getTransaction().begin();
			Query query = entityManager.createNamedQuery("checkUserSession");
			query.setParameter("login", login);
			query.setParameter("sessionID", id);

			try {
				user = (User) query.getSingleResult();
			} catch (NoResultException e) {
			}
			if (user != null)
				return true;
		}
		return false;
	}
}
