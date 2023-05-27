package com.locnp.mtsp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication(scanBasePackages = "com.locnp.mtsp")
@EnableAutoConfiguration
public class DeliveryAppAndroidApplication {

	public static void main(String[] args) {
		SpringApplication.run(DeliveryAppAndroidApplication.class, args);
	}
	
}
