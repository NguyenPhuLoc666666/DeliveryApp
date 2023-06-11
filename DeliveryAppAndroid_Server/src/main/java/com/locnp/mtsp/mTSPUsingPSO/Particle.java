package com.locnp.mtsp.mTSPUsingPSO;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class Particle {
	private int numOfPositions;
	private int numOfShippers;
	private HashMap<String, Integer> velocity;
	private HashMap<Integer, HashMap<String, Integer>> listOfVelocities;
	private double bestFitness;
	private HashMap<String, SubSolution> personalBestSolution;
	private HashMap<String, SubSolution> solution;
	private HashMap<Integer, HashMap<String, SubSolution>> ListOfSolutionBasedOnGroupPositions;
	private int currentIndexGroupOfPositions;

	public Particle(int numOfPositions, int numOfShippers) {
		this.numOfPositions = numOfPositions;
		this.numOfShippers = numOfShippers;
		this.velocity = new HashMap<>();
		this.bestFitness = Double.MAX_VALUE;
		this.personalBestSolution = new HashMap<>();
		this.ListOfSolutionBasedOnGroupPositions = new HashMap<>();
		this.listOfVelocities = new HashMap<>();
		this.solution = new HashMap<>();
	}

	public void updateCurrentIndexGroupOfPositions(int currentIndexGroupOfPositions) {
		this.setCurrentIndexGroupOfPositions(currentIndexGroupOfPositions);
		solution = ListOfSolutionBasedOnGroupPositions.get(currentIndexGroupOfPositions);
		if (!isEqualGroupOfPosition(solution)) {
			HashMap<String, SubSolution> cloneSolution = new HashMap<>(solution);
			setPersonalBestSolution(cloneSolution);
			setBestFitness(getCostOfSolution(cloneSolution));
		}
		velocity = listOfVelocities.get(currentIndexGroupOfPositions);
	}

	public void updateVelocity(HashMap<String, SubSolution> globalBestParticle, double w, double c1, double c2) {
		Random random1 = new Random();
		Random random2 = new Random();

		ArrayList<Position> personalListOfPositions;
		ArrayList<Position> globalListOfPositions;
		ArrayList<Position> currentListOfPositions;

		for (int i = 0; i < numOfShippers; i++) {
			personalListOfPositions = new ArrayList<>(personalBestSolution.get(String.valueOf(i)).getTour());
			globalListOfPositions = new ArrayList<>(globalBestParticle.get(String.valueOf(i)).getTour());
			currentListOfPositions = new ArrayList<>(solution.get(String.valueOf(i)).getTour());
			ArrayList<Position> samePositionList1 = new ArrayList<>(personalListOfPositions);
			samePositionList1.retainAll(currentListOfPositions);
			ArrayList<Position> samePositionList2 = new ArrayList<>(globalListOfPositions);
			samePositionList2.retainAll(currentListOfPositions);

			int sizeOfCurrentList = globalListOfPositions.size();
			int currentVelocity;
			if (velocity.get(String.valueOf(i)) == null)
				currentVelocity = 0;
			else
				currentVelocity = velocity.get(String.valueOf(i));

			int numOfPositions = (int) Math.round((w * currentVelocity
					+ (c1 * random1.nextDouble() * (personalListOfPositions.size() - samePositionList1.size())
							+ c2 * random2.nextDouble() * (globalListOfPositions.size() - samePositionList2.size()))
							/ 2)
					/ 2);
			if (numOfPositions > sizeOfCurrentList)
				numOfPositions = sizeOfCurrentList - 1;
			else if (numOfPositions < 0)
				numOfPositions = 1;

			velocity.put(String.valueOf(i), numOfPositions);
		}
	}

	private boolean isEqualGroupOfPosition(HashMap<String, SubSolution> solution) {
		if (solution == null) {
			System.out.println("solution null in particle class");
			return false;
		} else {
			for (int i = 0; i < numOfShippers; i++) {
				ArrayList<Position> currentList = new ArrayList<>(
						personalBestSolution.get(String.valueOf(i)).getTour());
				int currentPersonalGroupOfPosition = currentList.size();
				int currentSolutionGroupOfPosition = solution.get(String.valueOf(i)).getTour().size();
				if (currentPersonalGroupOfPosition != currentSolutionGroupOfPosition)
					return false;
			}
			return true;
		}
	}

	public void updatePosition(HashMap<String, SubSolution> globalSolution, HashMap<PositionPair, Double> distances,
			List<Position> positions, Position storeCoordinate) {
		int groupOfPosition;
		ArrayList<Position> visitedPositions = new ArrayList<Position>();
		ArrayList<Position> unvisitedPositions = new ArrayList<Position>(positions);
		ArrayList<Position> listOfPositions;
		ArrayList<Position> globalCurrentlistOfPositions;
		ArrayList<Position> listAfterUpdate;

		for (int i = 0; i < numOfShippers; i++) {
			int currentVelocity = velocity.get(String.valueOf(i));
			if (currentVelocity == 0) {
				globalCurrentlistOfPositions = new ArrayList<Position>(globalSolution.get(String.valueOf(i)).getTour());
				listOfPositions = new ArrayList<Position>(solution.get(String.valueOf(i)).getTour());
				if (getCostOfListPositions(distances, listOfPositions, storeCoordinate) < getCostOfListPositions(
						distances, globalCurrentlistOfPositions, storeCoordinate))
					for (int j = 0; j < listOfPositions.size(); j++) {
						visitedPositions.add(listOfPositions.get(j));
						unvisitedPositions.remove(listOfPositions.get(j));
					}
				else
					for (int j = 0; j < globalCurrentlistOfPositions.size(); j++) {
						visitedPositions.add(globalCurrentlistOfPositions.get(j));
						unvisitedPositions.remove(globalCurrentlistOfPositions.get(j));
					}
			}
			listOfPositions = new ArrayList<>();
		}
		for (int i = 0; i < numOfShippers; i++) {
			
			listAfterUpdate = new ArrayList<>();
			listOfPositions = new ArrayList<>(solution.get(String.valueOf(i)).getTour());

			groupOfPosition = listOfPositions.size();
			int currentVelocity = velocity.get(String.valueOf(i));
			globalCurrentlistOfPositions = new ArrayList<Position>(globalSolution.get(String.valueOf(i)).getTour());
			Position position = new Position();
			int num = groupOfPosition - currentVelocity;
			if (currentVelocity == 0) {
				if (getCostOfListPositions(distances, listOfPositions, storeCoordinate) < getCostOfListPositions(
						distances, globalCurrentlistOfPositions, storeCoordinate))
					listAfterUpdate = listOfPositions;
				else
					listAfterUpdate = globalCurrentlistOfPositions;
				ArrayList<Position> shuffedList = listAfterUpdate;
				Collections.shuffle(shuffedList);
				if (getCostOfListPositions(distances, shuffedList, storeCoordinate) < getCostOfListPositions(distances,
						listAfterUpdate, storeCoordinate))
					listAfterUpdate = shuffedList;
			} else {
				for (int j = 0; j < currentVelocity; j++) {

					if (currentVelocity < globalCurrentlistOfPositions.size())
						position = findPosition(visitedPositions, globalCurrentlistOfPositions.get(j).getPriority());
					else
						position = findPosition(visitedPositions, globalCurrentlistOfPositions
								.get(globalCurrentlistOfPositions.size() - 1).getPriority());

					if (position == null) {
						if (currentVelocity < globalCurrentlistOfPositions.size())
							position = findPosition(unvisitedPositions,
									globalCurrentlistOfPositions.get(j).getPriority());
						else
							position = findPosition(unvisitedPositions, globalCurrentlistOfPositions
									.get(globalCurrentlistOfPositions.size() - 1).getPriority());

						if (position == null)
							System.out.println("This position is not real!");
						else {
							listAfterUpdate.add(position);
							visitedPositions.add(position);
							unvisitedPositions.remove(position);
						}
					} else {
						j--;
						globalCurrentlistOfPositions.remove(position);
						if (globalCurrentlistOfPositions.size() == 0)
							globalCurrentlistOfPositions.add(unvisitedPositions.get(0));
					}
				}
				for (int j = 0; j < num; j++) {
					Collections.shuffle(listOfPositions);

					if (num < listOfPositions.size())
						position = findPosition(visitedPositions, listOfPositions.get(j).getPriority());
					else
						position = findPosition(visitedPositions,
								listOfPositions.get(listOfPositions.size() - 1).getPriority());

					if (position == null) {
						if (num < listOfPositions.size())
							position = findPosition(unvisitedPositions, listOfPositions.get(j).getPriority());
						else
							position = findPosition(unvisitedPositions,
									listOfPositions.get(listOfPositions.size() - 1).getPriority());

						if (position == null)
							System.out.println("This position is not real!");
						else {
							listAfterUpdate.add(position);
							visitedPositions.add(position);
							unvisitedPositions.remove(position);
						}
					} else {
						j--;
						listOfPositions.remove(position);
						if (listOfPositions.size() == 0)
							listOfPositions.add(unvisitedPositions.get(0));
					}
				}
				ArrayList<Position> shuffedList = listAfterUpdate;
				Collections.shuffle(shuffedList);
				if (getCostOfListPositions(distances, shuffedList, storeCoordinate) < getCostOfListPositions(distances,
						listAfterUpdate, storeCoordinate))
					listAfterUpdate = shuffedList;
			}

			double cost = getCostOfListPositions(distances, listAfterUpdate, storeCoordinate);
			SubSolution currentSubSolution = new SubSolution(listAfterUpdate, cost, distances);
			solution.put(String.valueOf(i), currentSubSolution);

		}
	}

	public void showSolution(HashMap<String, SubSolution> solution) {
		for (int m = 0; m < numOfShippers; m++) {
			SubSolution currentSolution = solution.get(String.valueOf(m));
			List<Position> list = currentSolution.getTour();
			int num = list.size();
			System.out.print("Shipper: " + m + "| ");
			for (int j = 0; j < num; j++) {
				System.out.print(list.get(j).getPriority() + " ");
			}
			System.out.println("distances cost: " + currentSolution.getCost());
			System.out.println();
		}
	}

	

	public double getCostOfListPositions(HashMap<PositionPair, Double> distances, ArrayList<Position> listOfPositions,
			Position storeCoordinate) {
		PositionPair positionKey;
		double distance = 0;
		int numOfPositionsEachShipper = listOfPositions.size();

		positionKey = new PositionPair(storeCoordinate, listOfPositions.get(0));
		distance += distances.get(positionKey);

		for (int j = 0; j < numOfPositionsEachShipper - 1; j++) {
			positionKey = new PositionPair(listOfPositions.get(j), listOfPositions.get(j + 1));
			distance += distances.get(positionKey);
		}
		positionKey = new PositionPair(listOfPositions.get(numOfPositionsEachShipper - 1), storeCoordinate);
		distance += distances.get(positionKey);
		return distance;
	}

	public boolean isExisted(ArrayList<Position> listOfPositions, int nextPosition) {
		int listOfPositionsSize = listOfPositions.size();
		if (listOfPositions != null) {
			for (int i = 0; i < listOfPositionsSize; i++) {
				if (listOfPositions.get(i).getPriority() == nextPosition)
					return true;
			}
		}
		return false;
	}

	public Position findPosition(ArrayList<Position> unvisitedPostions, int priority) {
		for (Position position : unvisitedPostions) {
			if (position.getPriority() == priority)
				return position;
		}
		return null;
	}

	public void updateFitness(double timeOfShift, double preparedTime, double velocityOfShipper) {
		double cost = getCostOfSolution(solution);
		boolean timeConstraint = isTimeOfShiftGreaterThanMovementTime(solution, timeOfShift, preparedTime,
				velocityOfShipper);
		if (cost < bestFitness) {
			setPersonalBestSolution(solution);
			this.setBestFitness(cost);
		}
	}

	private boolean isTimeOfShiftGreaterThanMovementTime(HashMap<String, SubSolution> currentSolution,
			double timeOfShift, double preparedTime, double velocityOfShipper) {
		for (int i = 0; i < numOfShippers; i++) {
			double cost = currentSolution.get(String.valueOf(i)).getCost();
			int sizeTour = currentSolution.get(String.valueOf(i)).getTour().size();
			double movementTime = getTotalMovermentTimeOfShipper(cost, sizeTour, preparedTime, velocityOfShipper);
			if (movementTime > timeOfShift)
				return false;
		}
		return true;
	}

	public double getTotalMovermentTimeOfShipper(double cost, int numOfPosition, double preparedTime,
			double velocityOfShipper) {
		return (cost / velocityOfShipper) + numOfPosition * preparedTime;
	}

	public double getCostOfSolution(HashMap<String, SubSolution> solution) {
		double cost = 0;
		for (int i = 0; i < numOfShippers; i++) {
			double distance = 0;
			distance += solution.get(String.valueOf(i)).getCost();
			cost += distance;
		}
		return cost;
	}

	public Particle clone() {
		Particle particle = new Particle(numOfPositions, numOfShippers);
		particle.setPersonalBestSolution(personalBestSolution);
		particle.setVelocity(velocity);
		particle.setBestFitness(bestFitness);
		return particle;
	}

	public boolean isParticleEquals(Particle particle) {
		if (this.getBestFitness() == particle.getBestFitness()
				&& this.isEqualGroupOfPosition(particle.getPersonalBestSolution()))
			return true;
		return false;
	}
	
	private void showListPosition(ArrayList<Position> listOfPositions) {
		for (Position position : listOfPositions) {
			System.out.print("> " + position.getPriority());
		}
		System.out.println();
	}

	public double getBestFitness() {
		return bestFitness;
	}

	public void setBestFitness(double bestFitness) {
		this.bestFitness = bestFitness;
	}

	public HashMap<String, SubSolution> getPersonalBestSolution() {
		return personalBestSolution;
	}

	public void setPersonalBestSolution(HashMap<String, SubSolution> personalBestSolution) {
		this.personalBestSolution = new HashMap<>(personalBestSolution);
	}

	public HashMap<String, SubSolution> getSolution() {
		return solution;
	}

	public void setSolution(HashMap<String, SubSolution> solution) {
		this.solution = solution;
	}

	public HashMap<Integer, HashMap<String, SubSolution>> getListOfSolutionBasedOnGroupPositions() {
		return ListOfSolutionBasedOnGroupPositions;
	}

	public void setListOfSolutionBasedOnGroupPositions(
			HashMap<Integer, HashMap<String, SubSolution>> listOfSolutionBasedOnGroupPositions) {
		ListOfSolutionBasedOnGroupPositions = listOfSolutionBasedOnGroupPositions;
	}

	public int getCurrentIndexGroupOfPositions() {
		return currentIndexGroupOfPositions;
	}

	public void setCurrentIndexGroupOfPositions(int currentIndexGroupOfPositions) {
		this.currentIndexGroupOfPositions = currentIndexGroupOfPositions;
	}

	public HashMap<String, Integer> getVelocity() {
		return velocity;
	}

	public void setVelocity(HashMap<String, Integer> velocity) {
		this.velocity = velocity;
	}

	public HashMap<Integer, HashMap<String, Integer>> getListOfVelocities() {
		return listOfVelocities;
	}

	public void setListOfVelocities(HashMap<Integer, HashMap<String, Integer>> listOfVelocities) {
		this.listOfVelocities = listOfVelocities;
	}
}
