package pl.pap.services;

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
	@GET
	@Path("/persistRoute")
	// Produces JSON as response
	@Produces(MediaType.APPLICATION_JSON)
	public String persistRoute(@QueryParam("login") String login,
			@QueryParam("sessionId") String sessionId,
			@QueryParam("route") String route) {
		System.out.println("Inside persistroute");
		System.out.println("Params: " + login + " " + sessionId + " " + route);

		if (sg.checkSession(login, sessionId)) {
			if (saveRoute(route)) {
				return Utility.constructJSON("persist", true);
			} else {
				return Utility.constructJSON("persist", false,
						"Database exception(Persist route)");
			}
		} else
			return Utility.constructJSON("persist", false,
					"Credentials problem");

		// saveRoute(route);
		// checkThisOut();
	}
	
	
	// HTTP Get Method
		@GET
		@Path("/updateRoute")
		// Produces JSON as response
		@Produces(MediaType.APPLICATION_JSON)
		public String updateRoute(@QueryParam("login") String login,
				@QueryParam("sessionId") String sessionId,
				@QueryParam("route") String route) {
			System.out.println("Inside updateRoute");
			System.out.println("Params: " + login + " " + sessionId + " " + route);

			if (sg.checkSession(login, sessionId)) {
				if (updateRoute(route)) {
					return Utility.constructJSON("update", true);
				} else {
					return Utility.constructJSON("update", false,
							"Database exception(Update route)");
				}
			} else
				return Utility.constructJSON("update", false,
						"Credentials problem");
		}
		
		private boolean updateRoute(String route){
			Gson gson = new Gson();
			Route routeModel = gson.fromJson(route, Route.class);
			System.out.println("Inside updateRoute");
			System.out.println("GSON: String: " + route);
			System.out.println(routeModel.getId());
			System.out.println(routeModel.getAuthor());
			System.out.println("rozmiar mapy markerow "
					+ routeModel.getMarkerMap().size());
			for (MarkerModel value : routeModel.getMarkerMap().values()) {
				System.out.println(value.getTitle());
			}
			try {
				 if(entityManager.find(Route.class, routeModel.getId()) == null){
			           throw new IllegalArgumentException("Unknown Route id");
			       }
				//entityManager.persist(routeModel);
				//entityManager.flush();
				 entityManager.merge(routeModel);
			} catch (PersistenceException pe) {
				System.out.println("=====ERROR CODE====");
				System.out.println(pe.getCause());
				return false;
			}
			return true;
		}

	private boolean saveRoute(String route) {
		Gson gson = new Gson();
		Route routeModel = gson.fromJson(route, Route.class);
		System.out.println("Inside saveRoute");
		System.out.println("GSON: String: " + route);
		System.out.println(routeModel);
		System.out.println(routeModel.getAuthor());
		System.out.println("rozmiar mapy markerow "
				+ routeModel.getMarkerMap().size());
		for (MarkerModel value : routeModel.getMarkerMap().values()) {
			// System.out.println("ID "+(MarkerModel)value.toString());
			System.out.println(value.getTitle());
		}
		try {
			entityManager.persist(routeModel);
			entityManager.flush();
		} catch (PersistenceException pe) {
			System.out.println("=====ERROR CODE====");
			System.out.println(pe.getCause());
			return false;
		}
		return true;

	}

	private void checkThisOut() {
		Route route2 = entityManager.find(Route.class, (long) 451);
		System.out.println(route2.getAuthor());
		System.out.println(route2.getMarkerMap().size());
		for (MarkerModel value : route2.getMarkerMap().values()) {
			// System.out.println("ID "+(MarkerModel)value.toString());
			System.out.println(value.getTitle());
		}

		Gson gson = new Gson();
		String ret = "";
		ret = gson.toJson(route2);
		System.out.println("Marshalled db entity: " + ret);

	}
}
