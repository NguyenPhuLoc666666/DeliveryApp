package com.locnp.mtsp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.locnp.mtsp.entity.PositionEntity;

@Repository
public interface PositionJpaRepo extends JpaRepository<PositionEntity, Integer> {

//	@Query("SELECT d FROM position_entity d WHERE d.test_data <= 25")
//	List<PositionEntity> findDataWithTestData25();
//
//	@Query("SELECT d FROM position_entity d WHERE d.test_data <= 50")
//	List<PositionEntity> findDataWithTestData50();
//
//	@Query("SELECT d FROM position_entity d WHERE d.test_data <= 100")
//	List<PositionEntity> findDataWithTestData100();
//
//	@Query("SELECT d FROM position_entity d WHERE d.test_data <= 200")
//	List<PositionEntity> findDataWithTestData200();

}
