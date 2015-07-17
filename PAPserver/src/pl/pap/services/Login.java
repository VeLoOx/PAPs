package pl.pap.services;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.Query;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import pl.pap.utils.Utility;
import pl.pap.model.User;

@Stateless
@LocalBean
// Path: http://localhost/<appln-folder-name>/login
@Path("/login")
public class Login {
	// Entity manager
	@PersistenceContext(unitName = "PAPserver", type = PersistenceContextType.TRANSACTION)
	EntityManager entityManager;

	// HTTP Get Method
	@GET
	// Path: http://localhost/<appln-folder-name>/login/dologin
	@Path("/dologin")
	// Produces JSON as response
	@Produces(MediaType.APPLICATION_JSON)
	// Query parameters are parameters:
	// http://localhost/<appln-folder-name>/login/dologin?username=abc&password=xyz
	public String doLogin(@QueryParam("login") String login,
			@QueryParam("password") String pwd) {
		System.out.println("Params: " + login + " " + pwd);
		String response = "";
		if (checkCredentials(login, pwd)) {
			response = Utility.constructJSON("login", true);
		} else {
			response = Utility.constructJSON("login", false,
					"Incorrect Email or Password");
		}
		return response;
	}

	@GET
	// Path: http://localhost/<appln-folder-name>/login/dologin
	@Path("/getuser")
	// Produces JSON as response
	@Produces(MediaType.APPLICATION_JSON)
	// Query parameters are parameters:
	// http://localhost/<appln-folder-name>/login/dologin?username=abc&password=xyz
	public User getUser() {
		System.out.println("Getting user id 1");
		return entityManager.find(User.class, (long) 1);
	}

	/**
	 * Method to check whether the entered credential is valid
	 *
	 * @param uname
	 * @param pwd
	 * @return
	 */
	private boolean checkCredentials(String login, String pwd) {
		System.out.println("Inside checkCredentials: " + login + " " + pwd);
		boolean result = false;
		User user = null;

		if (Utility.isNotNull(login) && Utility.isNotNull(pwd)) {
			/*
			 * try { result = DBConnection.checkLogin(login, pwd); //
			 * System.out.println("Inside checkCredentials try "+result); }
			 * catch (Exception e) { // TODO Auto-generated catch block //
			 * System.out.println("Inside checkCredentials catch"); result =
			 * false; } } else { //
			 * System.out.println("Inside checkCredentials else"); result =
			 * false; }
			 */
			System.out.println("LOGIN PASS NOT NULL");
			Query query = entityManager
					.createNamedQuery("checkUserCredentials");
			query.setParameter("login", login);
			query.setParameter("password", pwd);
			// Query query = entityManager.createNamedQuery("checkUser");
			// query.setParameter("log", login);
			System.out.println("QUERY VALUE");
			try {
				user = (User) query.getSingleResult();
			} catch (NoResultException e) {

			}

			if (user != null)
				result = true;
		}
		return result;
	}
}