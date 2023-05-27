package com.locnp.mtsp.mTSPUsingPSO;

import com.locnp.mtsp.entity.PositionEntity;

public class Position {
	private int id;
	private double latitude = 0;
	private double longtitude = 0;
	private boolean status = true;
	private int priority;
	// private int visted=-1; history
	private int testData;

	// private Info info = null;
	public Position() {

	}

	public Position(double depart, double destination) {
		this.latitude = depart;
		this.longtitude = destination;
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

	public Position(double latitude, double longitude, int priority, int testData) {
		this.latitude = latitude;
		this.longtitude = longitude;
		this.priority = priority;
		this.testData = testData;
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

	public double getLongitude() {
		return longtitude;
	}

	public void setLongitude(int longtitude) {
		this.longtitude = longtitude;
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

	public int getTestData() {
		return testData;
	}

	public void setTestData(int testData) {
		this.testData = testData;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}
		if (!(obj instanceof Position)) {
			return false;
		}
		Position other = (Position) obj;
		return (getLatitude() == other.getLatitude()) && (getLongitude() == other.getLongitude());
	}
}
