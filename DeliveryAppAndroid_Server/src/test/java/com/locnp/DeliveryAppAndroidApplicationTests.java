package com.locnp;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.locnp.mtsp.entity.PositionDao;
import com.locnp.mtsp.entity.PositionEntity;
import com.locnp.mtsp.repository.PositionRepository;

@SpringBootTest
class DeliveryAppAndroidApplicationTests {

	@Autowired
	private PositionDao positionDao;
	
	@Test
	void addPositionTest() {
		PositionEntity position = new PositionEntity();
		position.setLatitude(10.570045031835078);
		position.setLongitude(106.7728436477040);
		position.setPriority(2);
		position.setTestData(200);
		positionDao.save(position);
		
	}

}
