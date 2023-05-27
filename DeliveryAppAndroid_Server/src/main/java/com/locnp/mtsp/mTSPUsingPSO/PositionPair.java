package com.locnp.mtsp.mTSPUsingPSO;

import com.locnp.mtsp.entity.PositionEntity;

public class PositionPair {
	Position depart;
	Position destination;

	public PositionPair(Position depart, Position destination) {
		this.depart = depart;
		this.destination = destination;
	}

	public PositionPair(PositionEntity depart, PositionEntity destination) {
		this.depart = new Position(depart.getLatitude(),depart.getLongitude());
		this.destination = new Position(destination.getLatitude(),destination.getLongitude());
	}

	public void setDepart(Position depart) {
		this.depart = depart;
	}

	public String getDepart() {
		return this.depart.getCoordinate();
	}

	public void setDestination(Position destination) {
		this.destination = destination;
	}

	public String getDestination() {
		return this.destination.getCoordinate();
	}

	public Position getdepart() {
		return depart;
	}

	public Position getdestination() {
		return destination;
	}

	@Override
	public int hashCode() {
		int hash = 17;
		hash = hash * 31 + depart.getCoordinate().hashCode();
		hash = hash * 31 + destination.getCoordinate().hashCode();
		return hash;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}
		if (!(obj instanceof PositionPair)) {
			return false;
		}
		PositionPair other = (PositionPair) obj;
		boolean cordinate = (depart.getCoordinate().equals(other.depart.getCoordinate()) && destination.getCoordinate().equals(other.destination.getCoordinate())) 
				|| (depart.getCoordinate().equals(other.destination.getCoordinate()) && destination.getCoordinate().equals(other.depart.getCoordinate()));
		
		return cordinate;
	}
}

