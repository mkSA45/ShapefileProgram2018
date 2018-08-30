package models;

import com.vividsolutions.jts.geom.Geometry;

public class ShpMrtStation {
	private String name;
	private Geometry geomObj;
	
	public ShpMrtStation(String name) {
		super();
		this.name = name;
	}
	
	public ShpMrtStation(String name, Geometry geomObj) {
		super();
		this.name = name;
		this.geomObj = geomObj;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Geometry getGeomObj() {
		return geomObj;
	}

	public void setGeomObj(Geometry geomObj) {
		this.geomObj = geomObj;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		ShpMrtStation other = (ShpMrtStation) obj;
		if (name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!name.equals(other.name)) {
			return false;
		}
		return true;
	}
	
	
}
