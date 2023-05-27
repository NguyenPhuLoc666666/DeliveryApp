package com.locnp.mtsp.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "distances_entity")
public class DistancesEntity {
	public DistancesEntity(PositionEntity source, PositionEntity destination) {
		this.departLatitude = source.getLatitude();
		this.departLongitude = source.getLongitude();
		this.destinationLatitude = destination.getLatitude();
		this.destinationLongitude = destination.getLongitude();
		this.distance = 0;
		this.duration = 0;
		testData = 0;
	}
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "distances_id", nullable = false)
	int distances_id;
	@Column(name = "depart_latitude", nullable = false)
	double departLatitude;
	@Column(name = "depart_longitude", nullable = false)
	double departLongitude;
	@Column(name = "destination_latitude", nullable = false)
	double destinationLatitude;
	@Column(name = "destination_longitude", nullable = false)
	double destinationLongitude;
	@Column(name = "distance", nullable = false)
	double distance;
	@Column(name = "duration", nullable = false)
	double duration;
	@Column(name = "test_data", nullable = false)
	int testData;

}
