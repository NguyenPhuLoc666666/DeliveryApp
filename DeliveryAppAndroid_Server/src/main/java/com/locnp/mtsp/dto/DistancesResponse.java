package com.locnp.mtsp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data 
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DistancesResponse {
	int id;
	double departLatitude;
	double departLongitude;
	double destinationLatitude;
	double destinationLongitude;
	double distance;
	double duration;
	int testData;
}
