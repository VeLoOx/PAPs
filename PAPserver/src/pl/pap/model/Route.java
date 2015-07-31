package pl.pap.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Access;
import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.MapKey;
import javax.persistence.MapKeyColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import pl.pap.map.adapter.MyMapAdapter;

@Entity
@Table(name = "PAProute")
/*
 * @XmlRootElement
 * 
 * @XmlType(name = "route")
 * 
 * @XmlAccessorType(XmlAccessType.FIELD)
 */
public class Route {
	@Id
	@GeneratedValue
	// @Column(name="ROUTE_ID")
	private long ID;
	private String author;
	private String name;
	private String description;
	private String city;

	/*
	 * @ElementCollection ( fetch = FetchType.EAGER)
	 * 
	 * @CollectionTable(name="papmarkerlist",
	 * joinColumns=@JoinColumn(name="markerlistid"))
	 * 
	 * @Column(name="marker") private List<MarkerModel> markerList = new
	 * ArrayList<MarkerModel>();
	 * 
	 * public List<MarkerModel> getMarkerList() { return markerList; }
	 * 
	 * public void setMarkerList(List<MarkerModel> markerList) { this.markerList
	 * = markerList; }
	 */

	// @OneToMany(mappedBy="route",cascade = CascadeType.ALL)
	// @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	// @JoinTable(name = "B_MAPABYID", joinColumns =
	// @JoinColumn(columnDefinition="B_ID"))
	// @MapKey(name="MARKERID")

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

	public boolean addMarkerToMap(String id, MarkerModel marker) {
		return (markerMap.put(id, marker)) != null;
	}

	public boolean removeMarkerFromMap(String id) {
		return (markerMap.remove(id)) != null;
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
