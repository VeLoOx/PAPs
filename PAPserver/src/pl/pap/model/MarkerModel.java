package pl.pap.model;

import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

//@Entity
//@Table(name = "PAPmarker")
@Embeddable
//@Access(AccessType.FIELD)
/*@XmlType(name = "marker")
@XmlAccessorType(XmlAccessType.FIELD)*/
public class MarkerModel {
	//@Id
	//@GeneratedValue
	//private long ID;
	//@Column(name="MARKERID")
	private String markerId;
	private double lat;
	private double lng;
	private String title;
	private String snippet;
	
	
	//private Route route;
	
	/*public Route getRoute() {
	return route;
}

public void setRoute(Route route) {
	this.route = route;
}*/

	public String getMarkerId() {
		return markerId;
	}

	public void setMarkerId(String markerId) {
		this.markerId = markerId;
	}

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public double getLng() {
		return lng;
	}

	public void setLng(double lng) {
		this.lng = lng;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSnippet() {
		return snippet;
	}

	public void setSnippet(String snippet) {
		this.snippet = snippet;
	}
	
	



}
