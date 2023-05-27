package com.locnp.mtsp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data 
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PositionResponse {
	int id;
	double latitude;
	double longitude;
	int priority;
	int testData;
}
