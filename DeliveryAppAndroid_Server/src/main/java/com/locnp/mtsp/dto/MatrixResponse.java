package com.locnp.mtsp.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MatrixResponse {
	private int statusCode;
	private double source;
	private List<DestinationResponse> destinations;
	private double[][] durations;

	public double[][] to2DArrayDuration() {
		int size = durations.length;
		double[][] matrix = new double[size][size];

		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				matrix[i][j] = durations[i][j];
			}
		}
		return matrix;
	}

}
