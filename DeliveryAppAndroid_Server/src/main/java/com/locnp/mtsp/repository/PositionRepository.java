package com.locnp.mtsp.repository;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.locnp.mtsp.entity.PositionEntity;

@Repository
public interface PositionRepository extends CrudRepository<PositionEntity, Integer>{
	// List<PositionEntity> findByTestDataLessThanOrEqual(int testData);
}
