package com.locnp.mtsp.mTSPUsingPSO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SubSolution {
	private List<Position> tour = new ArrayList<Position>();
	private double cost = 0;
	HashMap<PositionPair, Double> distances = new HashMap<>();

	public SubSolution(List<Position> tour, double cost, HashMap<PositionPair, Double> distances) {
		this.tour = tour;
		this.cost = cost;
		this.distances = distances;
	}
	
	public SubSolution(List<Position> tour, double cost) {
		this.tour = tour;
		this.cost = cost;
		this.distances = null;
	}


	
	public List<Position> getTour() {
		return tour;
	}

	public void setTour(List<Position> tour) {
		this.tour = tour;
	}

	public double getCost() {
		return cost;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}
	
	public HashMap<PositionPair, Double> getDistances() {
		return distances;
	}

	public void setDistances(HashMap<PositionPair, Double> distances) {
		this.distances = distances;
	}
	
}

