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
		System.out.println("Inside SessionGuardian: checkCredentials: " + login
				+ " " + id);
		boolean result = false;
		User user = null;

		if (Utility.isNotNull(login) && Utility.isNotNull(id)) {
			System.out.println("LOGIN PASS NOT NULL");
			//entityManager.getTransaction().begin();
			Query query = entityManager.createNamedQuery("checkUserSession");
			query.setParameter("login", login);
			query.setParameter("sessionID", id);
			System.out.println("QUERY VALUE");
			System.out.println(query);
			try {
				user = (User) query.getSingleResult();
			} catch (NoResultException e) {
				System.out
						.println("CHECK SESSION: no user with given name and id");
			}

			if (user != null)
				result = true;
			//entityManager.getTransaction().commit();
			//entityManager.close();
		}
		return result;
	}
}
