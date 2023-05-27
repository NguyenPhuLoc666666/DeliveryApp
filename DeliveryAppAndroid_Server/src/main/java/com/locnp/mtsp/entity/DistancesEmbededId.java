package com.locnp.mtsp.entity;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class DistancesEmbededId implements Serializable {
	private static final long serialVersionUID = 8123096429193727L;
	double departLatitude;
	double departLongitude;
	double destinationLatitude;
	double destinationLongitude;

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		DistancesEmbededId that = (DistancesEmbededId) o;
		return departLatitude == that.departLatitude && departLongitude == that.departLongitude
				&& destinationLatitude == that.destinationLatitude && destinationLongitude == that.destinationLongitude;
	}

	@Override
	public int hashCode() {
		return Objects.hash(departLatitude, departLongitude, destinationLatitude, destinationLongitude);
	}
}
