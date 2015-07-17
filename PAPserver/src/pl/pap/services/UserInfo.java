package pl.pap.services;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import pl.pap.model.User;

@Path("/userinfo") 
public class UserInfo {
	  // HTTP Get Method
	  @GET
	  @Path("/getinfo") 
	  // Produces JSON as response
	  @Produces(MediaType.APPLICATION_JSON)
	  public User getUserInfo(){
		  User user = new User();
		  /*user.setName("jan");
		  user.setLogin("asd");
		  user.setPassword("asd");*/
		  
		  //Dopisać pobieranie danych
		  
		  System.out.println("User info called");
		  
		return user;
		  
	  }
}
