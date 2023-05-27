package com.locnp.mtsp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data 
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PositionRequest {

	double latitude;
	double longitude;
	int priority;
	int testData;
}
