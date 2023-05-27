package com.locnp.mtsp.dto;

import java.util.List;

import com.locnp.mtsp.mTSPUsingPSO.Position;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SubSolutionResponse {
	int shipperId;
	List<Position> tour;
	double cost;
}
