package models;

public class StnNameBusSvcNo {
	
	private String stationName;
	private String busSvcNo;
	private int direction;
	
	public StnNameBusSvcNo(String stationName, String busSvcNo) {
		super();
		this.stationName = stationName;
		this.busSvcNo = busSvcNo;
	}
	
	public StnNameBusSvcNo(String stationName, String busSvcNo, int direction) {
		super();
		this.stationName = stationName;
		this.busSvcNo = busSvcNo;
		this.direction = direction;
	}

	public String getStationName() {
		return stationName;
	}
	
	public void setStationName(String stationName) {
		this.stationName = stationName;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((busSvcNo == null) ? 0 : busSvcNo.hashCode());
		result = prime * result + direction;
		result = prime * result + ((stationName == null) ? 0 : stationName.hashCode());
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
		StnNameBusSvcNo other = (StnNameBusSvcNo) obj;
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
		if (stationName == null) {
			if (other.stationName != null) {
				return false;
			}
		} else if (!stationName.equals(other.stationName)) {
			return false;
		}
		return true;
	}
	
	
}
