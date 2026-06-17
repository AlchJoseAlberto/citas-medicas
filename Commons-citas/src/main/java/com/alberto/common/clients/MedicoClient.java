package com.alberto.common.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

import com.alberto.common.dto.MedicosResponse;


@FeignClient("msv-medicos")
public interface MedicoClient {
	@GetMapping("/{id}")
	MedicosResponse obtenerMedicoActivoPorId(@PathVariable Long id);
	
	@GetMapping("/id-medico/{id}")
	MedicosResponse obtenerMedicoSinEstadoPorId(@PathVariable Long id);
	
	@PutMapping("/{idMedico}/disponibilidad/{idDisponibilidad}")
	void actualizarDisponibilidadMedico(@PathVariable Long idMedico, @PathVariable Long idDisponibilidad);
	
	

}
