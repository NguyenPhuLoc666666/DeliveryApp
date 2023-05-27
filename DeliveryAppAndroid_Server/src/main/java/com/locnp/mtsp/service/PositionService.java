package com.locnp.mtsp.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.locnp.mtsp.dto.PositionRequest;
import com.locnp.mtsp.dto.PositionResponse;
import com.locnp.mtsp.entity.PositionEntity;
import com.locnp.mtsp.repository.PositionJpaRepo;
import com.locnp.mtsp.repository.PositionRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class PositionService {

	@Autowired
	private PositionRepository positionRepository = null;

	public void createPosition(PositionRequest positionRequest) {
		PositionEntity position = PositionEntity.builder().latitude(positionRequest.getLatitude())
				.longitude(positionRequest.getLongitude()).priority(positionRequest.getPriority())
				.testData(positionRequest.getTestData()).build();
		positionRepository.save(position);
		log.info("Position is saved!", position.getId());
	}

	public List<PositionResponse> getAllPositions() {
		List<PositionEntity> listPositions = (List<PositionEntity>) positionRepository.findAll();
		return listPositions.stream().map(position -> mapToPositionResponse(position)).toList();
	}

	private PositionResponse mapToPositionResponse(PositionEntity position) {
		return PositionResponse.builder().id(position.getId()).latitude(position.getLatitude())
				.longitude(position.getLongitude()).priority(position.getPriority()).testData(position.getTestData())
				.build();
	}

	public void createListOfPositions(List<PositionEntity> positions) {
		positionRepository.saveAll(positions);
		log.info("list of Position is saved!");
	}

	public List<PositionResponse> getDataWithTestData(int testData) {
		List<PositionEntity> listPositions = (List<PositionEntity>) positionRepository.findAll();
		List<PositionEntity> filtedlistPositions = new ArrayList<>();
		for(int i=0;i<1000;i++) {
			if(listPositions.get(i).getTestData()<=testData)
				filtedlistPositions.add(listPositions.get(i));
		}
		return filtedlistPositions.stream().map(position -> mapToPositionResponse(position)).toList();
	}
}
