package models;

import com.grum.geocalc.Coordinate;
import com.grum.geocalc.Point;

public class BusStop_v1 {
	
    private int POINT_NO;
    private double POINT_LATITUDE;
    private double POINT_LONGITUDE;
	private String busSvcNo;
	private int direction;
	private String busStopName;
	private String patternType;
	private int representation;
    
	private Coordinate lat;
	private Coordinate lng;
	private Point point;

	public BusStop_v1() {
		super();
	}
	
	public BusStop_v1(int POINT_NO, double POINT_LATITUDE, double POINT_LONGITUDE) {
		super();
		this.POINT_NO = POINT_NO;
		this.lat = Coordinate.fromDegrees(POINT_LATITUDE);
		this.lng = Coordinate.fromDegrees(POINT_LONGITUDE);
		this.point = Point.at(this.lat, this.lng);
	}
	
	public int getPOINT_NO() {
		return POINT_NO;
	}

	public void setPOINT_NO(int pOINT_NO) {
		POINT_NO = pOINT_NO;
	}
	
	public double getPOINT_LATITUDE() {
		return POINT_LATITUDE;
	}

	public void setPOINT_LATITUDE(double pOINT_LATITUDE) {
		lat = Coordinate.fromDegrees(pOINT_LATITUDE);
		POINT_LATITUDE = pOINT_LATITUDE;
		if(lat !=null && lng !=null) {
		point = Point.at(lat, lng);
		this.setPoint(point);
		}
	}

	public double getPOINT_LONGITUDE() {
		return POINT_LONGITUDE;
	}

	public void setPOINT_LONGITUDE(double pOINT_LONGITUDE) {
		lng = Coordinate.fromDegrees(pOINT_LONGITUDE);
		POINT_LONGITUDE = pOINT_LONGITUDE;
		if(lat !=null && lng !=null) {
		point = Point.at(lat, lng);
		this.setPoint(point);
		}
	}
	
	public String getBusSvcNo() {
		return busSvcNo;
	}
	
	public void setBusSvcNo(String busSvcNo) {
		this.busSvcNo = busSvcNo;
	}

	public int getDirection() {
		return direction;
	}

	public void setDirection(int direction) {
		this.direction = direction;
	}

	public String getBusStopName() {
		return busStopName;
	}

	public void setBusStopName(String busStopName) {
		this.busStopName = busStopName;
	}
	
	public String getPatternType() {
		return patternType;
	}

	public void setPatternType(String patternType) {
		this.patternType = patternType;
	}

	public int getRepresentation() {
		return representation;
	}

	public void setRepresentation(int representation) {
		this.representation = representation;
	}

	public Coordinate getLat() {
		return lat;
	}

	public void setLat(Coordinate lat) {
		this.lat = lat;
	}

	public Coordinate getLng() {
		return lng;
	}

	public void setLng(Coordinate lng) {
		this.lng = lng;
	}

	public Point getPoint() {
		return point;
	}

	public void setPoint(Point point) {
		this.point = point;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + POINT_NO;
		result = prime * result + ((busSvcNo == null) ? 0 : busSvcNo.hashCode());
		result = prime * result + direction;
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
		BusStop_v1 other = (BusStop_v1) obj;
		if (POINT_NO != other.POINT_NO) {
			return false;
		}
		if (busSvcNo == null) {
			if (other.busSvcNo != null) {
				return false;
			}
		} else if (!busSvcNo.equals(other.busSvcNo)) {
			return false;
		}
		if (direction != other.direction) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "BusStop [POINT_NO=" + POINT_NO + "]";
	}

}
