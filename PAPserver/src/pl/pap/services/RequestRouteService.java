package pl.pap.services;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.Query;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import pl.pap.model.MarkerModel;
import pl.pap.model.Route;
import pl.pap.utils.SessionGuardian;
import pl.pap.utils.Utility;

import com.google.gson.Gson;

@Stateless
@LocalBean
@Path("/requestRoute")
public class RequestRouteService {

	// Entity manager
	@PersistenceContext(unitName = "PAPserver", type = PersistenceContextType.TRANSACTION)
	EntityManager entityManager;
	String routeResponse;
	SessionGuardian sg = new SessionGuardian();

	// HTTP Get Method
	@GET
	@Path("/requestRoute")
	// Produces JSON as response
	@Produces(MediaType.APPLICATION_JSON)
	public String requestRoute(@QueryParam("login") String login,
			@QueryParam("sessionId") String sessionId,
			@QueryParam("autor") String autor, @QueryParam("id") String routeId) {
		System.out.println("Inside requestRoute");
		System.out.println("Params: " + login + " " + sessionId + " " + autor
				+ " " + routeId);
		if (sg.checkSession(login, sessionId)) {
			if (extractRoute(autor, Long.parseLong(routeId))) {
				System.out.println("Route response "+ routeResponse);
				// return routeResponse;
				return Utility
						.constructDataJSON("request", true, routeResponse);
			} else {
				return (Utility.constructJSON("request", false,
						"Database exception(Request route)"));
			}
		} else
			System.out.println("Credentials problem");
		return Utility.constructJSON("request", false, "Credentials problem");
	}

	// HTTP Get Method
	@GET
	@Path("/requestRoutesList")
	// Produces JSON as response
	@Produces(MediaType.APPLICATION_JSON)
	public String requestRoutesList(@QueryParam("login") String login,
			@QueryParam("sessionId") String sessionId) {
		System.out.println("Inside requestRoutesList");

		//extractRoutesList();

		if (sg.checkSession(login, sessionId)) {
			if (extractRoutesList()) {

				// return routeResponse;
				System.out.println("Route List response "+ routeResponse);
				return Utility
						.constructDataJSON("request", true, routeResponse);
			} else {
				return (Utility.constructJSON("request", false,
						"Database exception(Request route list)"));
			}
		} else
			System.out.println("Credentials problem");
		return Utility.constructJSON("request", false, "Credentials problem");

	}

	private boolean extractRoute(String autor, long routeId) {

		Route route2 = entityManager.find(Route.class, routeId);

		if (route2 == null) {
			System.out.println("Entity extraction fail(route)");
			return false;
		}

		System.out.println(route2.getAuthor());
		System.out.println(route2.getMarkerMap().size());
		for (MarkerModel value : route2.getMarkerMap().values()) {
			// System.out.println("ID "+(MarkerModel)value.toString());
			System.out.println(value.getTitle());
		}

		Gson gson = new Gson();
		String ret = "";
		ret = gson.toJson(route2);
		System.out.println("Marshalled route : " + ret);

		routeResponse = ret;
		return true;

	}

	private boolean extractRoutesList() {
		// List<Route> routesList = new ArrayList<Route>();
		// Query query = entityManager.createQuery("SELECT e FROM Route e");
		// routesList = (ArrayList<Route>) query.getResultList();
		List<Route> routesList = entityManager.createQuery(
				"SELECT r FROM Route r").getResultList();
		if (routesList != null) {
			for (Route route : routesList) {
				System.out.println(route.getId());
			}

			Gson gson = new Gson();
			String ret = gson.toJson(routesList);
			System.out.println("Marshalled routesList " + ret);
			routeResponse=ret;
			return true;
		}
		return false;

	}
}
