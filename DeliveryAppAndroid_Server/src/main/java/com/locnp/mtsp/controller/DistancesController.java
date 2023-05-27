package com.locnp.mtsp.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.locnp.mtsp.dto.DistancesRequest;
import com.locnp.mtsp.dto.DistancesResponse;
import com.locnp.mtsp.dto.MatrixResponse;
import com.locnp.mtsp.dto.PositionResponse;
import com.locnp.mtsp.dto.SubSolutionResponse;
import com.locnp.mtsp.entity.DistancesEntity;
import com.locnp.mtsp.entity.PositionEntity;
import com.locnp.mtsp.mTSPUsingPSO.PSO;
import com.locnp.mtsp.mTSPUsingPSO.Position;
import com.locnp.mtsp.mTSPUsingPSO.PositionPair;
import com.locnp.mtsp.mTSPUsingPSO.SubSolution;
import com.locnp.mtsp.service.DistancesService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/distances")
@RequiredArgsConstructor
@Slf4j
public class DistancesController {

	private final DistancesService distancesService;

	@PostMapping("/create")
	@ResponseStatus(HttpStatus.CREATED)
	private void createDistances(@RequestBody DistancesRequest distancesRequest) {
		distancesService.createDistances(distancesRequest);
	}

	@GetMapping("/getAllDistances")
	@ResponseStatus(HttpStatus.OK)
	private List<DistancesResponse> getAllDistances() {
		return distancesService.getAllDistances();
	}

	@PostMapping("/saveDistances")
	@ResponseStatus(HttpStatus.CREATED)
	private void saveDistances(@RequestBody List<DistancesEntity> distancesRequest) {
		distancesService.saveDistances(distancesRequest);
	}

	@GetMapping("/getSolution")
	@ResponseStatus(HttpStatus.OK)
	private void getSolution() {
		finalSolution = mTSPSolving();
		distancesService.getSolution(finalSolution);
	}
	
	@GetMapping("/getFinalSolution")
	@ResponseStatus(HttpStatus.OK)
	private List<SubSolutionResponse> getgetFinalSolutionSolution() {
		return distancesService.getSolution(finalSolution);
	}

	@GetMapping("/getTestDataDistances")
	@ResponseStatus(HttpStatus.OK)
	private List<DistancesResponse> getTestDataDistances(@RequestParam int testData) {
		return distancesService.getTestDistances(testData);
	}

	@Autowired
	private RestTemplate restTemplate;
	
	public List<PositionResponse> getListPositions() {
		// Call the /getAllPositions endpoint in MyController using RestTemplate
		String url = "http://localhost:9000/api/position/getTestData?testData="+50;
		ResponseEntity<List<PositionResponse>> responseGetTestData = restTemplate.exchange(url, HttpMethod.GET, null,
				new ParameterizedTypeReference<List<PositionResponse>>() {
				});
		List<PositionResponse> positionsResponse = responseGetTestData.getBody();
		return positionsResponse;
	}

	public List<DistancesResponse> getListDistances() {
		String url_distances = "http://localhost:9000/api/distances/getTestDataDistances?testData="+100;
		ResponseEntity<List<DistancesResponse>> responseGetDistances = restTemplate.exchange(url_distances,
				HttpMethod.GET, null, new ParameterizedTypeReference<List<DistancesResponse>>() {
				});
		List<DistancesResponse> distancesResponse = responseGetDistances.getBody();
		return distancesResponse;
	}

	@SuppressWarnings("null")
	public List<PositionEntity> convertListResponseToListPositionEntity(List<PositionResponse> positionsResponse) {
		System.out.println("-----------start convertListResponseToListPositionEntity");
		List<PositionEntity> positions = new ArrayList<>();
		for (PositionResponse response : positionsResponse) {
			PositionEntity position = new PositionEntity(response.getLatitude(), response.getLongitude());
			positions.add(position);
		}
		System.out.println("-----------end convertListResponseToListPositionEntity");
		return positions;
	}

	@SuppressWarnings("null")
	public List<Position> convertListResponseToListPosition(List<PositionResponse> positionsResponse) {
		System.out.println("-----------start convertListResponseToListPosition");
		List<Position> positions = new ArrayList<>();
		if (positionsResponse != null)
			for (PositionResponse response : positionsResponse) {
				Position position = new Position(response.getLatitude(), response.getLongitude(),
						response.getPriority(), response.getTestData());
				positions.add(position);
			}
		System.out.println("-----------end convertListResponseToListPosition");
		return positions;
	}

	private HashMap<String, SubSolution> finalSolution;

	public HashMap<String, SubSolution> mTSPSolving() {
		
		List<Position> positions = convertListResponseToListPosition(getListPositions());

		HashMap<PositionPair, Double> distances = convertListResponseToHashMapDuration(getListDistances());
		log.info("mtsp","|=================Start algorithms!===============|");
		int numOfParticles = 150;
		int numOfPositions = positions.size() - 1;
		int numOfShippers = 5;
		double c1 = 2;
		double c2 = 2;
		double wMax = 0.9;
		Position storeCoordinate = positions.get(0);
		PSO pso = new PSO(numOfParticles, numOfPositions, numOfShippers, distances, storeCoordinate, wMax, c1, c2);
		positions.remove(storeCoordinate);
		System.out.println("storeCoordinate: " + storeCoordinate.getCoordinate() + " | " + positions.size());
		pso.solvePSO(positions);

		HashMap<String, SubSolution> finalSolution = pso.getBestGlobalParticle().getPersonalBestSolution();
		System.out.println("----------finalSolution: " + pso.getBestGlobalParticle().getBestFitness());
		for (int i = 0; i < numOfShippers; i++) {
			SubSolution currentSolution = finalSolution.get(String.valueOf(i));
			List<Position> list = currentSolution.getTour();
			int num = list.size();
			System.out.print("Shipper: " + i + "| ");
			for (int j = 0; j < num; j++) {
				System.out.print(list.get(j).getPriority() + " ");
			}
			System.out.println("distances cost: " + currentSolution.getCost());
			System.out.println();
		}
		log.info("mtsp","|=================Done!===============|");
		return finalSolution;
	}

	public HashMap<PositionPair, Double> convertListResponseToHashMapDuration(List<DistancesResponse> listResponse) {
		System.out.println("-----------start convertListResponseToHashMapDistances: " + listResponse.size());
		HashMap<PositionPair, Double> distances = new HashMap<>();
		for (DistancesResponse response : listResponse) {
			PositionPair poskey = new PositionPair(
					new Position(response.getDepartLatitude(), response.getDepartLongitude()),
					new Position(response.getDestinationLatitude(), response.getDestinationLongitude()));
			distances.put(poskey, response.getDuration());
			//System.out.println("-----------end convert:" + distances.get(poskey));
		}
		System.out.println("-----------end convertListResponseToHashMapDistances: " + distances.size());
		return distances;
	}

//	public HashMap<PositionPair, Double> convertListResponseToListPosition(List<PositionResponse> positionsResponse) {
//		List<PositionEntity> positions = null;
//		for (PositionResponse response : positionsResponse) {
//			PositionEntity position = new PositionEntity(response.getLatitude(), response.getLongitude());
//			positions.add(position);
//		}
//		return positions;
//	}

	@PostMapping("/getDistancesDatafromMapbox")
	@ResponseStatus(HttpStatus.OK)
	private void getDistancesDatafromMapboxMatrixAPI() {

		List<Position> positions = convertListResponseToListPosition(getListPositions());
		System.out.println("positionsResponse: " + positions.size());
		String mapbox_access_token = "pk.eyJ1IjoiZHVjbmd1eWVuNjg4NiIsImEiOiJjbGdkYWg4MWExcjI1M3BvNmVldW4xZ2ZzIn0.UzKxHntHuS-8jDi3agcwLA";

		int innerLoop = positions.size();
		int outerLoop = innerLoop / 25 + 1;
		List<PositionEntity> destinations = new ArrayList<>();
		for (int line = 0; line < outerLoop; line++) {
			if (line < outerLoop - 1) {
				int col = line * 25 - line;
				for (int i = 0; i < innerLoop; i++) {
					System.out.println("time: " + (line + 1) + " row: " + (i + 1));

					PositionEntity source = new PositionEntity(positions.get(i).getLatitude(),
							positions.get(i).getLongitude());
					destinations.add(source);
					for (int j = 0; j < 24; j++) {
						PositionEntity destination = new PositionEntity(positions.get(col + j).getLatitude(),
								positions.get(col + j).getLongitude());

						destinations.add(destination);
					}
					//TO DO: testData 0->23 need one more col
					int testData = createNumberTestData(i, (int) (col + 24));
					callMapboxMatrixAPI(mapbox_access_token, source, destinations, testData);
					destinations.clear();
				}
			} else {
				int col = line * 25 - line;
				for (int i = 0; i < innerLoop; i++) {
					System.out.println("time: " + (line + 1) + " row: " + (i + 1));
					int num = innerLoop - col - 1;
					if (num != 0) {
						PositionEntity source = new PositionEntity(positions.get(i).getLatitude(),
								positions.get(i).getLongitude());
						destinations.add(source);
						for (int j = 0; j < num; j++) {
							PositionEntity destination = new PositionEntity(positions.get(col + j).getLatitude(),
									positions.get(col + j).getLongitude());
							destinations.add(destination);
						}
						int testData = createNumberTestData(i, col);
						callMapboxMatrixAPI(mapbox_access_token, source, destinations, testData);
						destinations.clear();
					}
				}
			}
		}
		//TO DO: result thiếu 25 điểm cho test 25, 100 cho test 100
		System.out.println("|=================CAll successful!===============|");
		
	}

	private String getNumOfPositionToCallAPI(int listSize) {
		String strNumOfPostion = "";
		if (listSize == 25)
			strNumOfPostion = "1;2;3;4;5;6;7;8;9;10;11;12;13;14;15;16;17;18;19;20;21;22;23;24";
		else {
			for (int i = 0; i < listSize - 1; i++) {
				strNumOfPostion += (i + 1);
				if (i < listSize - 2)
					strNumOfPostion += ";";
			}
		}
		return strNumOfPostion;
	}

	public void callMapboxMatrixAPI(String mapbox_access_token, PositionEntity source,
			List<PositionEntity> destinations, int testData) {
		String strNumOfPosition = getNumOfPositionToCallAPI(destinations.size());
		String urlmapbox = "https://api.mapbox.com/directions-matrix/v1/mapbox/driving/" + toCoordinates(destinations)
				+ "?sources=0&destinations=" + strNumOfPosition + "&access_token=" + mapbox_access_token;
		System.out.println("url: " + urlmapbox);
		try {
			Thread.sleep(1000);
			ResponseEntity<MatrixResponse> response = restTemplate.exchange(urlmapbox, HttpMethod.GET, null,
					new ParameterizedTypeReference<MatrixResponse>() {
					});
			if (response.getStatusCode().is2xxSuccessful()) {
				// Xử lý response thành công
				MatrixResponse responseBody = response.getBody();
				// destinations.remove(source);
				postDistancesToDB(source, destinations, responseBody, testData);

			} else {
				System.out.println("Failed: " + response.getStatusCode() + " call matrix mapbox api failed");
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void postDistancesToDB(PositionEntity source, List<PositionEntity> destinations, MatrixResponse response,
			int testData) {
		List<DistancesEntity> distances = new ArrayList<>();
		
		destinations.remove(0);
		int groupSize = destinations.size();
		
		System.out.println("groupSize:  " + groupSize);
		for (int j = 0; j < groupSize; j++) {

			DistancesEntity currentDistance = new DistancesEntity(source, destinations.get(j));

			currentDistance.setDistance(response.getDestinations().get(j).getDistance());
			currentDistance.setDuration(response.getDurations()[0][j]);

			currentDistance.setTestData(testData);
			distances.add(currentDistance);
			System.out.println(">>>>" + j + ">>>Distance: " + currentDistance.getDistance() + ">>>duration: "
					+ currentDistance.getDuration() + ">>>testData: " + currentDistance.getTestData());

		}
		distancesService.saveDistances(distances);
	}

	private int createNumberTestData(int i, int j) {

		int testData = 0;
		if (j < 25)
			testData = countTestData(i);
		else
			testData = countTestData(j);
		return testData;

	}

	private int countTestData(int j) {
		int testData = 0;
		if (j < 25)
			testData = 25;
		else if (j >= 25 && j < 50)
			testData = 50;
		else if (j >= 50 && j < 100)
			testData = 100;
		else if (j >= 100 && j < 200)
			testData = 200;
		else if (j >= 200 && j < 400)
			testData = 400;
		else if (j >= 400 && j < 800)
			testData = 800;
		else if (j >= 800 && j < 1000)
			testData = 1000;
		return testData;
	}

	private String toCoordinates(List<PositionEntity> destinations) {
		String strDestinations = "";
		int num = destinations.size();
		for (int i = 0; i < num; i++) {
			strDestinations += String.valueOf(destinations.get(i).getLongitude()) + ","
					+ String.valueOf(destinations.get(i).getLatitude());
			if (i < num - 1)
				strDestinations += ";";
		}
		return strDestinations;
	}
}
