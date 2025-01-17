package com.payoman.campaign;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class CampaignApplication {

	public static void main(String[] args) {
		SpringApplication.run(CampaignApplication.class, args);
	}

}
