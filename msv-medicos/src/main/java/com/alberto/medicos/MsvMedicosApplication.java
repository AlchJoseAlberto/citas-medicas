	package com.alberto.medicos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.alberto.medicos","com.alberto.common"})
public class MsvMedicosApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsvMedicosApplication.class, args);
	}

}
