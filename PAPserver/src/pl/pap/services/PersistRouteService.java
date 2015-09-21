package pl.pap.services;

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
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import pl.pap.model.MarkerModel;
import pl.pap.model.Route;
import pl.pap.model.User;
import pl.pap.utils.SessionGuardian;
import pl.pap.utils.Utility;

import com.google.gson.Gson;

@Stateless
@LocalBean
@Path("/persistRoute")
public class PersistRouteService {
	// Entity manager
	@PersistenceContext(unitName = "PAPserver", type = PersistenceContextType.TRANSACTION)
	EntityManager entityManager;

	// SesionGuardian
	SessionGuardian sg = new SessionGuardian();

	// HTTP Get Method
	@POST
	@Path("/persistRoute")
	// Produces JSON as response
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String persistRoute(@HeaderParam("login") String login,
			@HeaderParam("sessionId") String sessionId,
			// @FormParam("route")
			String route) {

		if (sg.checkSession(login, sessionId)) {
			if (saveRoute(route)) {
				return Utility.constructJSON("persist", true,
						"Route saved ");
			} else {
				return Utility.constructJSON("persist", false,
						"Database exception(Persist route)");
			}
		} else
			return Utility.constructJSON("persist", false,
					"Credentials problem");
	}

	private boolean saveRoute(String route) {
		Gson gson = new Gson();
		Route routeModel = gson.fromJson(route, Route.class);
		try {
			entityManager.persist(routeModel);
			entityManager.flush();
		} catch (PersistenceException pe) {
			return false;
		}
		return true;

	}
}
