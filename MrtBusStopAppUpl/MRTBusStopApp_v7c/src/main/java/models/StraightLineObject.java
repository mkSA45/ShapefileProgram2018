package models;

import com.vividsolutions.jts.geom.Geometry;

public class StraightLineObject implements Comparable<StraightLineObject>{
	private String POINT_NO;
	private String stationName;
	private double distance;
	private Geometry busStopCoord;
	private Geometry mrtStationCoord;
	private String exitLetter;
	private String busSvcNo;
	private int direction;
	private String busStopName;

	public StraightLineObject(String pOINT_NO, String stationName, double distance, String exitLetter) {
		super();
		POINT_NO = pOINT_NO;
		this.stationName = stationName;
		this.distance = distance;
		this.exitLetter = exitLetter;
	}

	public StraightLineObject(String pOINT_NO, String stationName, double distance, Geometry busStopCoord,
			Geometry mrtStationCoord) {
		super();
		POINT_NO = pOINT_NO;
		this.stationName = stationName;
		this.distance = distance;
		this.busStopCoord = busStopCoord;
		this.mrtStationCoord = mrtStationCoord;
	}

	public String getPOINT_NO() {
		return POINT_NO;
	}

	public void setPOINT_NO(String pOINT_NO) {
		POINT_NO = pOINT_NO;
	}

	public String getStationName() {
		return stationName;
	}

	public void setStationName(String stationName) {
		this.stationName = stationName;
	}

	public double getDistance() {
		return distance;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}

	public Geometry getBusStopCoord() {
		return busStopCoord;
	}

	public void setBusStopCoord(Geometry busStopCoord) {
		this.busStopCoord = busStopCoord;
	}

	public Geometry getMrtStationCoord() {
		return mrtStationCoord;
	}

	public void setMrtStationCoord(Geometry mrtStationCoord) {
		this.mrtStationCoord = mrtStationCoord;
	}

	public String getExitLetter() {
		return exitLetter;
	}

	public void setExitLetter(String exitLetter) {
		this.exitLetter = exitLetter;
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

	@Override
	public int compareTo(StraightLineObject o) {
		if (this.distance < o.distance)
			return -1;
		else if (this.distance > o.distance)
			return +1;
		else
			return 0;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((POINT_NO == null) ? 0 : POINT_NO.hashCode());
		result = prime * result + ((busStopCoord == null) ? 0 : busStopCoord.hashCode());
		long temp;
		temp = Double.doubleToLongBits(distance);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((mrtStationCoord == null) ? 0 : mrtStationCoord.hashCode());
		result = prime * result + ((stationName == null) ? 0 : stationName.hashCode());
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
		StraightLineObject other = (StraightLineObject) obj;
		if (POINT_NO == null) {
			if (other.POINT_NO != null)
				return false;
		} else if (!POINT_NO.equals(other.POINT_NO))
			return false;
		if (busStopCoord == null) {
			if (other.busStopCoord != null)
				return false;
		} else if (!busStopCoord.equals(other.busStopCoord))
			return false;
		if (Double.doubleToLongBits(distance) != Double.doubleToLongBits(other.distance))
			return false;
		if (mrtStationCoord == null) {
			if (other.mrtStationCoord != null)
				return false;
		} else if (!mrtStationCoord.equals(other.mrtStationCoord))
			return false;
		if (stationName == null) {
			if (other.stationName != null)
				return false;
		} else if (!stationName.equals(other.stationName))
			return false;
		return true;
	}

//	@Override
//	public int hashCode() {
//		final int prime = 31;
//		int result = 1;
//		result = prime * result + POINT_NO;
//		result = prime * result + ((busStopCoord == null) ? 0 : busStopCoord.hashCode());
//		result = prime * result + ((busSvcNo == null) ? 0 : busSvcNo.hashCode());
//		result = prime * result + direction;
//		long temp;
//		temp = Double.doubleToLongBits(distance);
//		result = prime * result + (int) (temp ^ (temp >>> 32));
//		result = prime * result + ((mrtStationCoord == null) ? 0 : mrtStationCoord.hashCode());
//		result = prime * result + ((stationName == null) ? 0 : stationName.hashCode());
//		return result;
//	}
//
//	@Override
//	public boolean equals(Object obj) {
//		if (this == obj) {
//			return true;
//		}
//		if (obj == null) {
//			return false;
//		}
//		if (getClass() != obj.getClass()) {
//			return false;
//		}
//		StraightLineObject other = (StraightLineObject) obj;
//		if (POINT_NO != other.POINT_NO) {
//			return false;
//		}
//		if (busStopCoord == null) {
//			if (other.busStopCoord != null) {
//				return false;
//			}
//		} else if (!busStopCoord.equals(other.busStopCoord)) {
//			return false;
//		}
//		if (busSvcNo == null) {
//			if (other.busSvcNo != null) {
//				return false;
//			}
//		} else if (!busSvcNo.equals(other.busSvcNo)) {
//			return false;
//		}
//		if (direction != other.direction) {
//			return false;
//		}
//		if (Double.doubleToLongBits(distance) != Double.doubleToLongBits(other.distance)) {
//			return false;
//		}
//		if (mrtStationCoord == null) {
//			if (other.mrtStationCoord != null) {
//				return false;
//			}
//		} else if (!mrtStationCoord.equals(other.mrtStationCoord)) {
//			return false;
//		}
//		if (stationName == null) {
//			if (other.stationName != null) {
//				return false;
//			}
//		} else if (!stationName.equals(other.stationName)) {
//			return false;
//		}
//		return true;
//	}
	
	
}
