package com.locnp;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.locnp.model.PositionDao;
import com.locnp.model.PositionModel;
import com.locnp.model.PositionRepository;

@SpringBootTest
class DeliveryAppAndroidApplicationTests {

	@Autowired
	private PositionDao positionDao;
	
	@Test
	void addPositionTest() {
		PositionModel position = new PositionModel();
		position.setLatitude(10.570045031835078);
		position.setLongitude(106.7728436477040);
		position.setPriority(2);
		position.setTestData(200);
		positionDao.save(position);
		
	}

}
