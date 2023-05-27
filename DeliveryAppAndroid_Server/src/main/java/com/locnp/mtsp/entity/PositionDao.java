package com.locnp.mtsp.entity;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;

import com.locnp.mtsp.repository.PositionRepository;

@Service
public class PositionDao {

	@Autowired
	private PositionRepository repository;

	public void save(PositionEntity positionModel) {
		repository.save(positionModel);
	}

	public List<PositionEntity> getAllPosition() {
		List<PositionEntity> positions = new ArrayList<>();
		Streamable.of(repository.findAll()).forEach(position -> {
			positions.add(position);
		});
		return positions;
	}

	public void delete(PositionEntity positionModel) {
		repository.delete(positionModel);

	}

}
