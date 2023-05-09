package com.locnp.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.locnp.model.PositionDao;
import com.locnp.model.PositionModel;

@RestController
public class PositionController {

	@Autowired
	private PositionDao positionDao;
	
	@GetMapping("/position/get-all")
	private List<PositionModel> getAllPositions() {
		return positionDao.getAllPosition();
	}
	
	@PostMapping("/position/save")
	private void getPosition(@RequestBody PositionModel positionModel) {
		positionDao.save(positionModel);
	}
	
	@PostMapping("/position/initialize-1000-positions")
	public void get1000Positions() {
		Random randLat = new Random();
		Random randLong = new Random();
		double minLat = 10.809731845263862;
		double maxLat = 10.866028502876551;
		double minLong = 106.75338073329344;
		double maxLong = 106.85301401545887;
		
		int numOfPositions =1000;
		ArrayList<PositionModel> list = new ArrayList<>();
			for (int i = 0; i < numOfPositions; i++) {
				double latitude = randLat.nextDouble()*(maxLat-minLat) + minLat;
				double longtitude = randLong.nextDouble()*(maxLong-minLong)+minLong;
				PositionModel currentPos = new PositionModel();
				currentPos.setLatitude(latitude);
				currentPos.setLongitude(longtitude);
				currentPos.setPriority(i);
				if(i>= 0 && i<100) currentPos.setTestData(100);
				if(i>= 100 && i<200) currentPos.setTestData(200);
				if(i>= 200 && i<400) currentPos.setTestData(400);
				if(i>= 400 && i<800) currentPos.setTestData(800);
				if(i>= 800 && i<1000) currentPos.setTestData(1000);
				list.add(currentPos);
				positionDao.save(currentPos);
			}
		for (PositionModel positionModel : list) {
			System.out.println("\nid: "+positionModel.getId()+
					"\npriority: "+positionModel.getPriority()+
					"\nlatitude: "+positionModel.getLatitude()+
					"\nlongitude: "+positionModel.getLongitude()+
					"\ntest data: "+positionModel.getTestData());
		};	
			
	}
}
