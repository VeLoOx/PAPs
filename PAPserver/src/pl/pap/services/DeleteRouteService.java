package pl.pap.services;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import pl.pap.model.Route;
import pl.pap.utils.SessionGuardian;
import pl.pap.utils.Utility;

@Stateless
@LocalBean
@Path("/deleteRoute")
public class DeleteRouteService {

	// Entity manager
	@PersistenceContext(unitName = "PAPserver", type = PersistenceContextType.TRANSACTION)
	EntityManager entityManager;
	SessionGuardian sg = new SessionGuardian();

	// HTTP Get Method
	@DELETE
	@Path("/deleteRoute")
	// Produces JSON as response
	@Produces(MediaType.APPLICATION_JSON)
	public String requestRoute(@QueryParam("login") String login,
			@QueryParam("sessionId") String sessionId,
			@QueryParam("id") String routeId) {
		System.out.println("Inside deleteRoute");
		System.out
				.println("Params: " + login + " " + sessionId + " " + routeId);
		if (sg.checkSession(login, sessionId)) {
			if (removeRoute(Long.parseLong(routeId))) {
				// return routeResponse;
				return Utility.constructDataJSON("delete", true,
						"Route deleted ");
			} else {
				return (Utility.constructJSON("request", false,
						"Database exception(Delete route)"));
			}
		} else
			System.out.println("Credentials problem");
		return Utility.constructJSON("delete", false, "Credentials problem");
	}

	private boolean removeRoute(long routeId) {

		Route route2 = entityManager.find(Route.class, routeId);
		if (route2 != null) {
			System.out.println("Trying to remove entity");
			entityManager.remove(route2);
			System.out.println("Removed");
			return true;
		}
		System.out.println("Nothing to remove");
		return false;
	}

}
