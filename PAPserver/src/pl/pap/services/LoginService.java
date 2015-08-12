package pl.pap.services;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.zip.CRC32;

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
public class LoginService {
	// Entity manager
	@PersistenceContext(unitName = "PAPserver", type = PersistenceContextType.TRANSACTION)
	EntityManager entityManager;
	String sessionId;

	// HTTP Get Method
	@GET
	// Path: http://localhost/<appln-folder-name>/login/dologin
	@Path("/dologin")
	// Produces JSON as response
	@Produces(MediaType.APPLICATION_JSON)
	public String doLogin(@QueryParam("login") String login,
			@QueryParam("password") String pwd) {
		System.out.println("LoginService");
		System.out.println("Params: " + login + " " + pwd);
		String response = "";
		if (checkCredentials(login, pwd)) {
			response = Utility.constructDataJSON("login", true, sessionId);
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
		User user = null;

		if (Utility.isNotNull(login) && Utility.isNotNull(pwd)) {
			System.out.println("LOGIN PASS NOT NULL");
			Query query = entityManager
					.createNamedQuery("checkUserCredentials");
			query.setParameter("login", login);
			query.setParameter("password", pwd);
			// Query query = entityManager.createNamedQuery("checkUser");
			// query.setParameter("log", login);
			try {
				user = (User) query.getSingleResult();
			} catch (NoResultException e) {

			}

			if (user != null)		
				generateSessionId(login);
				user.setSessionID(sessionId);
				System.out.println("User sessionId after update "+sessionId);
				return true;
		}
		
		return false;
	}

	private void generateSessionId(String userName) {
		System.out.println("Inside generateId");
		CRC32 crc = new CRC32();
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		System.out.println(dateFormat.format(date)); //2014/08/06 15:59:48
		String tmp=dateFormat.format(date)+userName;
		System.out.println("data and name "+tmp );
		byte bytes[]=tmp.getBytes();
		crc.update(bytes, 0, bytes.length);
		System.out.println("CRC from data and name " + crc.getValue());
		sessionId=Long.toString(crc.getValue());
		//return Long.toString(crc.getValue());
	}
}