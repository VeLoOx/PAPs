package pl.pap.model;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "PAProute")
public class Route {
	@Id
	@GeneratedValue
	private long ID;
	private String author;
	private String name;
	private String description;
	private String city;

	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "papmarkerlist", joinColumns = @JoinColumn(name = "routeid"))
	@Column(name = "marker")
	private Map<String, MarkerModel> markerMap = new HashMap<String, MarkerModel>();

	public Map<String, MarkerModel> getMarkerMap() {
		return markerMap;
	}

	public void setMarkerMap(HashMap<String, MarkerModel> markerMap) {
		this.markerMap = markerMap;
	}

	public Long getId() {
		return ID;
	}

	public void setId(Long ID) {
		this.ID = ID;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
