package models;

import com.vividsolutions.jts.geom.Geometry;

public class BusStop {
	
    private String POINT_NO;
    private Geometry geomObj;

	public BusStop() {
		super();
	}

	public BusStop(String pOINT_NO, Geometry geomObj) {
		super();
		POINT_NO = pOINT_NO;
		this.geomObj = geomObj;
	}
	
	

	public String getPOINT_NO() {
		return POINT_NO;
	}

	public void setPOINT_NO(String pOINT_NO) {
		POINT_NO = pOINT_NO;
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
		result = prime * result + ((POINT_NO == null) ? 0 : POINT_NO.hashCode());
		result = prime * result + ((geomObj == null) ? 0 : geomObj.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BusStop other = (BusStop) obj;
		if (POINT_NO == null) {
			if (other.POINT_NO != null)
				return false;
		} else if (!POINT_NO.equals(other.POINT_NO))
			return false;
		if (geomObj == null) {
			if (other.geomObj != null)
				return false;
		} else if (!geomObj.equals(other.geomObj))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "BusStop [POINT_NO=" + POINT_NO + "]";
	}

}
