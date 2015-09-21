package pl.pap.services;

import java.sql.SQLException;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import pl.pap.model.User;
import pl.pap.utils.Utility;

@Stateless
@LocalBean
// Path: http://localhost/<appln-folder-name>/register
@Path("/register")
public class RegisterService {
	// Entity manager
	@PersistenceContext(unitName = "PAPserver", type = PersistenceContextType.TRANSACTION)
	EntityManager entityManager;

	// HTTP Post Method
	@POST
	// Path: http://localhost/<appln-folder-name>/register/doregister
	@Path("/doregister")
	// Produces JSON as response
	@Produces(MediaType.APPLICATION_JSON)
	public String doRegister(@FormParam("login") String login,
			@FormParam("password") String pwd) {
		String response = "";
		if (!Utility.isNotNull(login) || !Utility.isNotNull(pwd)) {
			response = Utility.constructJSON("register", false,
					"Login or Password can NOT be empty");
		} else if (Utility.isSpecialCharacter(login)
				|| Utility.isSpecialCharacter(pwd)) {

			response = Utility
					.constructJSON("register", false,
							"Special Characters are not allowed in Username and Password");
		} else if (isUserRegistered(login)) {
			response = Utility.constructJSON("register", false,
					"You are already registered");
		} else if (registerUser(login, pwd)) {
			response = Utility.constructJSON("register", true);
		} else {
			response = Utility
					.constructJSON("register", false, "Error occured");
		}

		return response;

	}

	private boolean isUserRegistered(String login) {
		User user = null;
		Query query = entityManager.createNamedQuery("checkUser");
		query.setParameter("login", login);

		try {
			user = (User) query.getSingleResult();
		} catch (NoResultException e) {

		}
		if (user != null)
			return true;

		return false;
	}

	private boolean registerUser(String login, String pwd) {
		User user = new User();
		user.setLogin(login);
		user.setPassword(pwd);

		try {
			entityManager.persist(user);
			entityManager.flush();
			return true;
		} catch (PersistenceException pe) {
			return false;
		}
	}

}
