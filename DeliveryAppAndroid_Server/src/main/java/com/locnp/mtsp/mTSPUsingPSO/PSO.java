package com.locnp.mtsp.mTSPUsingPSO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class PSO {
	private int numOfParticles;
	private int numOfPositions;
	private int numOfShippers;
	private HashMap<PositionPair, Double> distances;
	private Particle[] particles;
	private Particle globalBestParticle;
	private Particle personalBestParticle;
	private int minNumOfPosition;
	private Position storeCoordinate;
	private ArrayList<int[]> groupOfPositions;
	private int currentIndexGroupOfPositions;
	private double c1;
	private double c2;
	private double w;
	private HashMap<String, Integer> currentParticleVelocity;

	private double velocityOfShipper = 30;
	private double timeOfShift = 2;
	private double preparedTime = 0.17; /* = 10 minutes = 0.17*60 */
	private double acceptedRange = 0.25;
	private int loop = 0;
	private int numOfLoops = 200;

	public PSO(int numOfParticles, int numOfPositions, int numOfShippers, HashMap<PositionPair, Double> distances,
			Position storeCoordinate, double w, double c1, double c2) {
		System.out.println("-----------start create constructor solution");
		this.numOfParticles = numOfParticles;
		this.numOfPositions = numOfPositions;
		this.numOfShippers = numOfShippers;
		this.distances = distances;
		this.particles = new Particle[numOfParticles];
		this.globalBestParticle = new Particle(numOfPositions, numOfShippers);
		this.personalBestParticle = new Particle(numOfPositions, numOfShippers);
		this.storeCoordinate = storeCoordinate;
		this.w = w;
		this.c1 = c1;
		this.c2 = c2;
		do {
			minNumOfPosition = (int) Math.ceil(this.numOfPositions / this.numOfShippers);
			if (minNumOfPosition == 1) {
				this.numOfShippers = this.numOfPositions / 2;
			} else if (minNumOfPosition == 0) {
				this.numOfShippers = this.numOfPositions / 4;
			}
		} while (this.numOfPositions < this.numOfShippers);
		this.groupOfPositions = new ArrayList<>();
		this.currentParticleVelocity = new HashMap<>();
		this.currentIndexGroupOfPositions = 0;
	}

	public void solvePSO(List<Position> positions) {

		initializeParticles(positions);
		showBestPersonalSolution();
		do {
			for (int i = 0; i < numOfParticles; i++) {
				Particle currentParticle = particles[i].clone();
				if (currentParticle.getBestFitness() < globalBestParticle.getBestFitness()

				/*
				 * &&
				 * isTimeOfShiftGreaterThanMovementTime(currentParticle.getPersonalBestSolution(
				 * ))
				 */) {
					globalBestParticle = currentParticle.clone();
				}
			}

			currentIndexGroupOfPositions = getCurrentIndexGroupOfPositions(
					globalBestParticle.getPersonalBestSolution());

			if (currentIndexGroupOfPositions == -1)
				System.out.println(">> Group of Positions not found!");

			int stopCondition = 0;
			for (int i = 0; i < numOfParticles; i++) {
				Particle currentParticle = particles[i];
				
				currentParticle.updateCurrentIndexGroupOfPositions(currentIndexGroupOfPositions);
				currentParticle.updateVelocity(globalBestParticle.getPersonalBestSolution(), w, c1, c2);
				currentParticle.updatePosition(globalBestParticle.getPersonalBestSolution(), distances, positions,
						storeCoordinate);
				currentParticle.updateFitness(timeOfShift, preparedTime, velocityOfShipper);

				if (currentParticle.getBestFitness() < globalBestParticle.getBestFitness()) {
					globalBestParticle = currentParticle.clone();
				}

				if (currentParticle.getBestFitness() - acceptedRange < globalBestParticle.getBestFitness()
						&& currentParticle.getBestFitness() + acceptedRange > globalBestParticle.getBestFitness()) {
					stopCondition++;
				}
			}
			if (stopCondition == numOfParticles) {
				System.out.println("|>>====================Done!====================<<|");
				return;
			}
			loop++;

		} while (loop < numOfLoops);
		showBestPersonalSolution();
		System.out.println("\n.............show final solution: " + globalBestParticle.getBestFitness());
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

	private void showBestPersonalSolution() {
		int i = 0;
		for (Particle particle : particles) {
			System.out.println(
					"--particle[" + (i++) + "]: " + particle.getCostOfSolution(particle.getPersonalBestSolution()));
		}
	}

	public void showParticleCost(Particle particle) {
		System.out.print(">>>" + particle.getBestFitness());
	}

	private int getCurrentIndexGroupOfPositions(HashMap<String, SubSolution> globalBestParticle) {
		int groupOfPositionsSize = groupOfPositions.size();
		for (int i = 0; i < groupOfPositionsSize; i++) {
			if (isEqualGroupOfPosition(groupOfPositions.get(i), globalBestParticle))
				return i;
		}
		return -1;
	}

	private boolean isEqualGroupOfPosition(int[] groupOfPositions, HashMap<String, SubSolution> globalBestSolution) {
		for (int j = 0; j < numOfShippers - 1; j++) {
			if (groupOfPositions[j] != globalBestSolution.get(String.valueOf(j)).getTour().size())
				return false;
		}
		return true;
	}

	private void initializeParticles(List<Position> positions) {
		Random random = new Random();
		Collections.shuffle(positions, random);

		initializeGroupOfPositions(positions);

		HashMap<String, SubSolution> solutionAfterDivison;

		for (int i = 0; i < numOfParticles; i++) {
			particles[i] = new Particle(numOfPositions, numOfShippers);
			for (int j = 0; j < groupOfPositions.size(); j++) {

				int[] arrayNumOfPositions = groupOfPositions.get(j);

				solutionAfterDivison = getRandomSolution(distances, positions, arrayNumOfPositions);
				if (getCostOfSolution(solutionAfterDivison) < particles[i].getBestFitness()) {
					particles[i].setPersonalBestSolution(solutionAfterDivison);
					particles[i].setBestFitness(getCostOfSolution(solutionAfterDivison));
					particles[i].setVelocity(currentParticleVelocity);
				}
				particles[i].setSolution(solutionAfterDivison);
				particles[i].getListOfVelocities().put(j, currentParticleVelocity);
				particles[i].getListOfSolutionBasedOnGroupPositions().put(j, solutionAfterDivison);
				particles[i].updateFitness(timeOfShift, preparedTime, velocityOfShipper);
			}
		}

	}

	private boolean compareConstraintMovementTime(HashMap<String, SubSolution> solutionAfterDivison) {
		List<Boolean> constraintMovementTimeList = getConstraintMovementTimeSolution(solutionAfterDivison);
		for (Boolean movementTime : constraintMovementTimeList) {
			if (movementTime == false)
				return false;
		}
		return true;
	}

	private List<Boolean> getConstraintMovementTimeSolution(HashMap<String, SubSolution> solutionAfterDivison) {
		List<Boolean> constraintMovementTimeList = new ArrayList<>();
		List<Position> currentList = new ArrayList<>();
		int numOfPositions;
		double movementTime;
		for (int i = 0; i < numOfShippers; i++) {
			currentList = solutionAfterDivison.get(String.valueOf(i)).getTour();
			numOfPositions = currentList.size();
			movementTime = getTotalMovermentTimeOfShipper(getCostOfListPositions(distances, currentList),
					numOfPositions, preparedTime, velocityOfShipper);
			if (movementTime > timeOfShift)
				constraintMovementTimeList.add(i, false);
			else
				constraintMovementTimeList.add(i, true);
		}
		if (constraintMovementTimeList.size() != numOfShippers)
			System.out.println("Error: constraintMovementTimeList add wrong at getConstraintMovementTimeSolution");
		return constraintMovementTimeList;
	}

	public double getTotalMovermentTimeOfShipper(double cost, int numOfPosition, double preparedTime,
			double velocityOfShipper) {
		return (cost / velocityOfShipper) + numOfPosition * preparedTime;
	}

	public HashMap<String, SubSolution> getRandomSolution(HashMap<PositionPair, Double> distances,
			List<Position> positions, int[] arrayNumOfPositions) {
		HashMap<String, SubSolution> solutionAfterDivison = new HashMap<>();
		ArrayList<Position> currentListOfPositions = new ArrayList<Position>(positions);
		ArrayList<Position> tour = new ArrayList<>();
		SubSolution currentSubSolution;
		HashMap<PositionPair, Double> subDistances;
		double cost;
		int numOfPosition;

		for (int i = 0; i < numOfShippers; i++) {
			Random random = new Random();
			Collections.shuffle(currentListOfPositions, random);
			currentParticleVelocity.put(String.valueOf(i), null);
			if (i < numOfShippers - 1) {
				numOfPosition = arrayNumOfPositions[i];

				for (int j = 0; j < numOfPosition; j++) {
					tour.add(currentListOfPositions.get(j));
				}

				subDistances = getSubMatrix(distances, tour);
				cost = getCostOfListPositions(distances, tour);
				
				currentSubSolution = new SubSolution(tour, cost, subDistances);
				solutionAfterDivison.put(String.valueOf(i), currentSubSolution);

				for (int k = 0; k < numOfPosition; k++) {
					currentListOfPositions.remove(0);
				}

				tour = new ArrayList<>();
			} else if (i == numOfShippers - 1) {
				tour = new ArrayList<>(currentListOfPositions);
				subDistances = getSubMatrix(distances, tour);
				cost = getCostOfListPositions(distances, tour);

				currentListOfPositions.clear();
				currentSubSolution = new SubSolution(tour, cost, subDistances);
				solutionAfterDivison.put(String.valueOf(numOfShippers - 1), currentSubSolution);
			}
		}
		return solutionAfterDivison;
	}

	public void initializeGroupOfPositions(List<Position> positions) {
		int numOfPostionCurrentListOfPosition;
		Random random = new Random();

		for (int particleIteration = 0; particleIteration < numOfParticles; particleIteration++) {
			int numOfPosition;
			int[] currentArrayNumOfOrders = new int[numOfShippers - 1];
			numOfPosition = 0;
			numOfPostionCurrentListOfPosition = positions.size();
			for (int h = 0; h < numOfShippers - 1; h++) {
				numOfPostionCurrentListOfPosition -= numOfPosition;
				int maxNumOfPosition = numOfPostionCurrentListOfPosition - (numOfShippers - h - 1) * minNumOfPosition;
				numOfPosition = (int) (random.nextDouble() * (maxNumOfPosition - minNumOfPosition) + minNumOfPosition);
				currentArrayNumOfOrders[h] = numOfPosition;
			}
			if (!isExistedInGroupOfPositions(currentArrayNumOfOrders)) {
				groupOfPositions.add(currentArrayNumOfOrders);
			}
		}
		// uniqueListGroupOfPositions(groupOfPositions);
	}

	public boolean isExistedInGroupOfPositions(int[] checkingArrayNumOfPositions) {
		int groupOfPositionsSize = groupOfPositions.size();
		if (groupOfPositionsSize != 0) {
			for (int i = 0; i < groupOfPositionsSize; i++) {
				if (Arrays.equals(groupOfPositions.get(i), checkingArrayNumOfPositions))
					return true;
			}
			return false;
		} else
			return false;
	}

	public void uniqueListGroupOfPositions(ArrayList<int[]> groupOfPositions) {
		for (int[] sortedArr : groupOfPositions) {
			insertionSort(sortedArr);
		}
		int[] tempArr;
		for (int i = 0; i < groupOfPositions.size(); i++) {
			tempArr = groupOfPositions.get(i);
			for (int j = i + 1; j < groupOfPositions.size(); j++) {
				if (isEqualsBetween2Arr(tempArr, groupOfPositions.get(j))) {
					groupOfPositions.remove(j);
					j--;
				}
			}
		}
	}

	public boolean isEqualsBetween2Arr(int[] arr1, int[] arr2) {
		int arr1Len = arr1.length;
		int arr2Len = arr2.length;
		if (arr1Len == arr2Len) {
			for (int i = 0; i < arr1Len; i++) {
				if (arr1[i] != arr2[i])
					return false;
			}
			return true;
		}
		return false;
	}

	public void insertionSort(int[] arr) {
		int n = arr.length;
		for (int i = 1; i < n; i++) {
			int key = arr[i];
			int j = i - 1;
			while (j >= 0 && arr[j] > key) {
				arr[j + 1] = arr[j];
				j--;
			}
			arr[j + 1] = key;
		}
	}

	public double getCostOfListPositions(HashMap<PositionPair, Double> distances, List<Position> currentList) {
		PositionPair positionKey;
		double distance = 0;
		int numOfPositionsEachShipper = currentList.size();

		positionKey = new PositionPair(storeCoordinate, currentList.get(0));
		distance += distances.get(positionKey);

		for (int j = 0; j < numOfPositionsEachShipper - 1; j++) {
			positionKey = new PositionPair(currentList.get(j), currentList.get(j + 1));
			distance += distances.get(positionKey);
		}
		positionKey = new PositionPair(currentList.get(numOfPositionsEachShipper - 1), storeCoordinate);
		distance += distances.get(positionKey);
		return distance;
	}

	public double getCostOfSolution(HashMap<String, SubSolution> solution) {
		double cost = 0;
		for (int i = 0; i < numOfShippers; i++) {
			double distance = 0;
			distance += getCostOfListPositions(distances, solution.get(String.valueOf(i)).getTour());
			cost += distance;
		}
		return cost;
	}

	public HashMap<PositionPair, Double> getSubMatrix(HashMap<PositionPair, Double> distances,
			ArrayList<Position> subPositions) {
		HashMap<PositionPair, Double> subDistanceMatrix = new HashMap<>();
		int num = subPositions.size();
		PositionPair positionKey;
		for (int i = 0; i < num; i++) {
			for (int j = 0; j < num; j++) {
				positionKey = new PositionPair(subPositions.get(i), subPositions.get(j));
				subDistanceMatrix.put(positionKey, distances.get(positionKey));
			}
		}
		return subDistanceMatrix;
	}

	public Particle getBestGlobalParticle() {
		return globalBestParticle;
	}

	public Particle getBestPersonalParticle() {
		return personalBestParticle;
	}
}
