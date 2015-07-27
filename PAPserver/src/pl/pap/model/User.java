package pl.pap.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@NamedQueries({
@NamedQuery(name = "checkUserCredentials", 
query = "SELECT u " +
        "FROM User u "+
		"WHERE u.login = :login and u.password= :password" ),

@NamedQuery(name = "checkUser", 
query = "SELECT u " +
        "FROM User u "+
		"WHERE u.login = :login" ),



@NamedQuery(name = "checkUserSession", 
query = "SELECT u " +
        "FROM User u "+
		"WHERE u.login = :login and u.sessionID=:sessionID" )
}
)
@XmlRootElement(name = "user")
@XmlType(propOrder = {"name", "login", "password" })
@Entity
@Table(name = "PAPuser")
public class User {
	@Id
	@GeneratedValue
	private long ID;
	@Column(name="USER_NAME")
	private String name;
	@Column(name="USER_LOGIN")
	private String login;
	@Column(name="USER_PASSWORD")
	private String password;
	
	private String sessionID;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getSessionID() {
		return sessionID;
	}
	public void setSessionID(String sessionID) {
		this.sessionID = sessionID;
	}


}
