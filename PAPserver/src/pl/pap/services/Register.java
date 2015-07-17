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
import javax.ws.rs.GET;
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
public class Register {
	// Entity manager
	@PersistenceContext(unitName = "PAPserver", type = PersistenceContextType.TRANSACTION)
	EntityManager entityManager;

	// HTTP Get Method
	@GET
	// Path: http://localhost/<appln-folder-name>/register/doregister
	@Path("/doregister")
	// Produces JSON as response
	@Produces(MediaType.APPLICATION_JSON)
	// Query parameters are parameters:
	// http://localhost/<appln-folder-name>/register/doregister?name=pqrs&username=abc&password=xyz
	public String doRegister(@QueryParam("name") String name,
			@QueryParam("login") String login,
			@QueryParam("password") String pwd) {
		String response = "";
		// System.out.println("Inside doLogin "+uname+"  "+pwd);
		if (!Utility.isNotNull(login) || !Utility.isNotNull(pwd)) {
			response = Utility
					.constructJSON("register", false,
							"Login or Password can NOT be empty");
		}
		else if (Utility.isSpecialCharacter(login)
				|| Utility.isSpecialCharacter(pwd)
				|| Utility.isSpecialCharacter(name)) {
			
			response = Utility
					.constructJSON("register", false,
							"Special Characters are not allowed in Username and Password");
		} 
		else if (isUserRegistered(login)) {
			response = Utility.constructJSON("register", false,
					"You are already registered");
		} 
		else if (registerUser(name, login, pwd)) {
			response = Utility.constructJSON("register", true);
		} 
		else {
			response = Utility
					.constructJSON("register", false, "Error occured");
		}

		return response;

	}

	private boolean isUserRegistered(String login) {
		System.out.println("isUserRegistered");
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

	private boolean registerUser(String name, String login, String pwd) {
		System.out.println("Inside registerUser params: " + name + " " + login
				+ " " + pwd);
		User user = new User();
		user.setName(name);
		user.setLogin(login);
		user.setPassword(pwd);
		
			try {
				entityManager.persist(user);
				entityManager.flush();
				return true;
			} catch (PersistenceException pe) {
				System.out.println("=====ERROR CODE====");
				System.out.println(pe.getCause());
				return false;
			}
	}

}