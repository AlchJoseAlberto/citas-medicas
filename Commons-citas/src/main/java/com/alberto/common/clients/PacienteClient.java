package com.alberto.common.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.alberto.common.dto.PacienteResponse;

@FeignClient("msv-pacientes")
public interface PacienteClient {
	@GetMapping("/id-paciente/{id}")
	PacienteResponse obtenerPacienteSinEstadoPorId(@PathVariable Long id);
	
	@GetMapping("/{id}")
	PacienteResponse obtenerPacienteActivoPorId(@PathVariable Long id);
	
	
	
	

}
