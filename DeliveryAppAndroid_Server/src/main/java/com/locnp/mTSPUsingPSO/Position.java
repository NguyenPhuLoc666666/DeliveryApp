package com.locnp.mTSPUsingPSO;

public class Position {
	private double latitude = 0;
	private double longtitude = 0;
	private boolean status = true;
	private int priority;
	//private int visted=-1; history

	

	// private Info info = null;
	public Position() {

	}

	public Position(int latitude, int longtitude) {
		this.latitude = latitude;
		this.longtitude = longtitude;
	}

	public Position(String strCoordinate) {
		String[] coordinate = strCoordinate.split(",");
		this.latitude = Double.parseDouble(coordinate[0]);
		this.longtitude = Double.parseDouble(coordinate[1]);
	}
	
	public Position(String strCoordinate, int priority) {
		String[] coordinate = strCoordinate.split(",");
		this.latitude = Double.parseDouble(coordinate[0]);
		this.longtitude = Double.parseDouble(coordinate[1]);
		this.priority = priority;
	}

	public String getCoordinate() {
		return this.latitude + ", " + this.longtitude;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(int latitude) {
		this.latitude = latitude;
	}

	public double getLongtitude() {
		return longtitude;
	}

	public void setLongtitude(int longtitude) {
		this.longtitude = longtitude;
	}

	public boolean isEqual(Position position) {
		if (this.getLatitude() == position.getLatitude() && this.getLongtitude() == position.getLongtitude())
			return true;
		return false;
	}

	public void changeStatus() {
		if (this.status == false)
			this.setStatus(true);
		else
			this.setStatus(false);
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}
	

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}
}

