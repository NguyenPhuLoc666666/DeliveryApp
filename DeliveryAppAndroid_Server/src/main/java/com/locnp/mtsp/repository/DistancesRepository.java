package com.locnp.mtsp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.locnp.mtsp.dto.DistancesResponse;
import com.locnp.mtsp.entity.DistancesEntity;

@Repository
public interface DistancesRepository extends JpaRepository<DistancesEntity, Integer>{
	//JPQL
//	@Query(value = "SELECT d FROM distances_entity d WHERE d.testData <= ?1")
//	List<DistancesResponse> getTestData(int testData);
}