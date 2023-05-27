package com.locnp.mtsp.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.locnp.mtsp.dto.PositionRequest;
import com.locnp.mtsp.dto.PositionResponse;
import com.locnp.mtsp.entity.PositionEntity;
import com.locnp.mtsp.service.PositionService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/position")
@RequiredArgsConstructor
@Slf4j
public class PositionController {

	private final PositionService positionService;
	
	@PostMapping("/create")
	@ResponseStatus(HttpStatus.CREATED)
	private void createPosition(@RequestBody PositionRequest positionRequest) {
		positionService.createPosition(positionRequest);
	}
	
	@GetMapping("/getAllPositions")
	@ResponseStatus(HttpStatus.OK)
	private List<PositionResponse> getAllPositions() {
		return positionService.getAllPositions();
	}
	
	@GetMapping("/getTestData200")
	@ResponseStatus(HttpStatus.OK)
	private List<PositionResponse> getTestData() {
		List<PositionResponse> listPositions = positionService.getDataWithTestData(200);
		List<PositionResponse> filtedlistPositions = new ArrayList<>();
		for(int i=0;i<1000;i++) {
			if(listPositions.get(i).getTestData()<=200)
				filtedlistPositions.add(listPositions.get(i));
		}
		return filtedlistPositions;
	}
	
	
	@PostMapping("/initialize-1000-positions")
	@ResponseStatus(HttpStatus.CREATED)
	public void get1000Positions() {
		 List<PositionEntity> positions = getRandomPositions();
	        positionService.createListOfPositions(positions);
	}
	
	public List<PositionEntity> getRandomPositions() {
		Random randLat = new Random();
		Random randLong = new Random();
		double minLat =  10.837163410406477;
		double maxLat = 10.899543981150824; 
		double minLong = 106.72027707250122;
		double maxLong = 106.78337145665077;
		
		int numOfPositions =1000;
		PositionEntity currentPos;
		List<PositionEntity> listOfPositions = new ArrayList<>();
			for (int i = 0; i < numOfPositions; i++) {
				double latitude = randLat.nextDouble()*(maxLat-minLat) + minLat;
				double longtitude = randLong.nextDouble()*(maxLong-minLong)+minLong;
				currentPos = new PositionEntity();
				currentPos.setLatitude(latitude);
				currentPos.setLongitude(longtitude);
				currentPos.setPriority(i+1);
				if(i>= 0 && i<25) currentPos.setTestData(25);
				if(i>= 25 && i<50) currentPos.setTestData(50);
				if(i>= 50 && i<100) currentPos.setTestData(100);
				if(i>= 100 && i<200) currentPos.setTestData(200);
				if(i>= 200 && i<400) currentPos.setTestData(400);
				if(i>= 400 && i<800) currentPos.setTestData(800);
				if(i>= 800 && i<1000) currentPos.setTestData(1000);
				listOfPositions.add(currentPos);
			}
		for (int i =0;i<200;i++) {
			log.info("currentPos","\nid: "+listOfPositions.get(i).getId()+
					"\npriority: "+listOfPositions.get(i).getPriority()+
					"\nlatitude: "+listOfPositions.get(i).getLatitude()+
					"\nlongitude: "+listOfPositions.get(i).getLongitude()+
					"\ntest data: "+listOfPositions.get(i).getTestData());
		};	
		
		return listOfPositions;
	}
	
	@GetMapping("/getTestData")
	@ResponseStatus(HttpStatus.OK)
	private List<PositionResponse> getTestData(@RequestParam int testData) {
		List<PositionResponse> listPositions = positionService.getDataWithTestData(testData);
		return listPositions;
	}
	
}
