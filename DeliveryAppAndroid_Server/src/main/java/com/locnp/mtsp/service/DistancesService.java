package com.locnp.mtsp.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.locnp.mtsp.dto.DistancesRequest;
import com.locnp.mtsp.dto.DistancesResponse;
import com.locnp.mtsp.dto.SubSolutionResponse;
import com.locnp.mtsp.entity.DistancesEntity;
import com.locnp.mtsp.entity.PositionEntity;
import com.locnp.mtsp.mTSPUsingPSO.Position;
import com.locnp.mtsp.mTSPUsingPSO.SubSolution;
import com.locnp.mtsp.repository.DistancesRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class DistancesService {
	@Autowired
	private final DistancesRepository distancesRepository = null;

	public void createDistances(DistancesRequest distancesRequest) {
		DistancesEntity distances = DistancesEntity.builder().departLatitude(distancesRequest.getDepartLatitude())
				.departLongitude(distancesRequest.getDepartLongitude())
				.destinationLatitude(distancesRequest.getDestinationLatitude())
				.destinationLongitude(distancesRequest.getDestinationLongitude())
				.distance(distancesRequest.getDistance()).duration(distancesRequest.getDuration())
				.testData(distancesRequest.getTestData()).build();
		distancesRepository.save(distances);
		log.info("Distances is saved!");
	}

	public List<DistancesResponse> getAllDistances() {
		List<DistancesEntity> listOfDistances = (List<DistancesEntity>) distancesRepository.findAll();
		return listOfDistances.stream().map(distances -> mapToDistancesResponse(distances)).toList();
	}

	private DistancesResponse mapToDistancesResponse(DistancesEntity distances) {
		return DistancesResponse.builder().departLatitude(distances.getDepartLatitude())
				.departLongitude(distances.getDepartLongitude()).destinationLatitude(distances.getDestinationLatitude())
				.destinationLongitude(distances.getDestinationLongitude()).distance(distances.getDistance())
				.duration(distances.getDuration()).testData(distances.getTestData()).build();
	}

	public void saveDistances(List<DistancesEntity> distances) {
		distancesRepository.saveAll(distances);
		log.info("Test Distances is saved!");
	}

	public List<DistancesResponse> getTestDistances(int testData) {
		List<DistancesEntity> listOfDistances = (List<DistancesEntity>) distancesRepository.findAll();
		List<DistancesEntity> filtedlistPositions = new ArrayList<>();
		for(int i=0;i<listOfDistances.size();i++) {
			if(listOfDistances.get(i).getTestData()<= testData)
				filtedlistPositions.add(listOfDistances.get(i));
		}
		return filtedlistPositions.stream().map(distances -> mapToDistancesResponse(distances)).toList();
	}

	public List<DistancesResponse> getTestDistancesMapbox(int testData) {
		List<DistancesEntity> listOfDistances = (List<DistancesEntity>) distancesRepository.findAll();
		return listOfDistances.stream().map(distances-> mapToDistancesResponse(distances)).toList();
	}

	public List<SubSolutionResponse> getSolution(HashMap<String, SubSolution> finalSolution) {
		int num = finalSolution.size();
		List<SubSolution> list = new ArrayList<>();
		List<SubSolutionResponse> listResponse = new ArrayList<>();
		SubSolution currentSubSolution;
		for (int i = 0; i<num; i++) {
			List<Position> tour = new ArrayList<>(finalSolution.get(String.valueOf(i)).getTour());
			currentSubSolution = new SubSolution(
					tour, finalSolution.get(String.valueOf(i)).getCost());
			list.add(currentSubSolution);
			listResponse.add(mapToSubSolutionResponse(currentSubSolution));
		}
		return listResponse;
	}
	

	private SubSolutionResponse mapToSubSolutionResponse(SubSolution subsolution) {
		return SubSolutionResponse.builder()
				.tour(subsolution.getTour())
				.cost(subsolution.getCost())
				.build();
	}
	
	
}
