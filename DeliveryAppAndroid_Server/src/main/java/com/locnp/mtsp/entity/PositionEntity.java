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
@Table(name = "position_entity")
public class PositionEntity {
	public PositionEntity(double latitude, double longitude) {
		this.latitude = latitude;
		this.longitude = longitude;
		this.priority = -1;
		this.testData = 0;
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "position_id", nullable = false)
	int id;
	@Column(name = "latitude", nullable = false)
	double latitude;
	@Column(name = "longitude", nullable = false)
	double longitude;
	@Column(name = "priority", nullable = false)
	int priority;
	@Column(name = "test_data", nullable = false)
	int testData;
	
	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}
		if (!(obj instanceof PositionEntity)) {
			return false;
		}
		PositionEntity other = (PositionEntity) obj;
		 
		return (getLatitude() ==other.getLatitude()) && (getLongitude()==other.getLongitude());	
	}
}
