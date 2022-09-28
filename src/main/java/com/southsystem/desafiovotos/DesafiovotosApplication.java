package com.southsystem.desafiovotos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@EnableFeignClients
@SpringBootApplication
@EnableCaching
public class DesafiovotosApplication {


	public static void main(String[] args) {
		SpringApplication.run(DesafiovotosApplication.class, args);
		
		
		
	}

}
