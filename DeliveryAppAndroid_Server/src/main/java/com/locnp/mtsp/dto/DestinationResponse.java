package com.locnp.mtsp.dto;

import com.locnp.mtsp.entity.PositionEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DestinationResponse {
private String name;
private double distance;
private PositionEntity position;
}
