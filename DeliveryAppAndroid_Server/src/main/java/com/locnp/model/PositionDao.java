package com.locnp.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;

@Service
public class PositionDao {

	@Autowired
	private PositionRepository repository;

	public void save(PositionModel positionModel) {
		repository.save(positionModel);
	}

	public List<PositionModel> getAllPosition() {
		List<PositionModel> positions = new ArrayList<>();
		Streamable.of(repository.findAll()).forEach(position -> {
			positions.add(position);
		});
		return positions;
	}

	public void delete(PositionModel positionModel) {
		repository.delete(positionModel);

	}

}
