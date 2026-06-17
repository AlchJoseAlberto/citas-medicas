package com.alberto.pacientes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.alberto.pacientes", "com.alberto.common"})
public class MsvPacientesApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsvPacientesApplication.class, args);
	}

}
