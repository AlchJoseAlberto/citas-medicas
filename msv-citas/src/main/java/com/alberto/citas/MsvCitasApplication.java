package com.alberto.citas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.alberto.citas", "com.alberto.common"})
public class MsvCitasApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsvCitasApplication.class, args);
	}

}
