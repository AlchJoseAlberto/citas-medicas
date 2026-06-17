package com.alberto.common.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient("msv-citas")
public interface CitaClient {
	@GetMapping("/id-medico/{idMedico}/citas-asignadas")
	void medicoTieneCitasAsignadas(@PathVariable Long idMedico);
	
	@GetMapping("/id-paciente/{idPaciente}/citas-asigandas")
	void pacienteTieneCitasAsignadas(@PathVariable Long idPaciente);
	
	

}
