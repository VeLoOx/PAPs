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

		if (checkCredentials(login, pwd)) {
			return Utility.constructDataJSON("login", true, sessionId);
		} else {
			return Utility.constructJSON("login", false,
					"Incorrect Email or Password");
		}
	}
	
	/**
	 * Method to check whether the entered credential is valid
	 *
	 * @param uname
	 * @param pwd
	 * @return
	 */
	private boolean checkCredentials(String login, String pwd) {
		User user = null;

		if (Utility.isNotNull(login) && Utility.isNotNull(pwd)) {
			Query query = entityManager
					.createNamedQuery("checkUserCredentials");
			query.setParameter("login", login);
			query.setParameter("password", pwd);
			try {
				user = (User) query.getSingleResult();
			} catch (NoResultException e) {

			}

			if (user != null) {
				generateSessionId(login);
				user.setSessionID(sessionId);
				return true;
			}
		}

		return false;
	}

	private void generateSessionId(String userName) {
		CRC32 crc = new CRC32();
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		String tmp = dateFormat.format(date) + userName;
		byte bytes[] = tmp.getBytes();
		crc.update(bytes, 0, bytes.length);
		sessionId = Long.toString(crc.getValue());
	}
}