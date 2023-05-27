package com.locnp.mtsp.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.locnp.mtsp.service.PositionService;

@Configuration
public class PositionConfig {
	@Bean
	public PositionService positionBean() {
		return new PositionService();
	}

//	    @Bean
//	    public ModelMapper modelMapperBean() {
//	        return new ModelMapper();
//	    }

}
