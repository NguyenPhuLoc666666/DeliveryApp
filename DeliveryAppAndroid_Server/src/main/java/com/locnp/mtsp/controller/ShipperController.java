package com.locnp.mtsp.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.locnp.mtsp.entity.PositionEntity;

public class ShipperController {
	@PostMapping("/initialize-200-shippers")
	@ResponseStatus(HttpStatus.CREATED)
	public void get200Shippers() {
//		 List<PositionEntity> positions = getRandomPositions();
//	        shipperService.createListOfPositions(positions);
	}
}
