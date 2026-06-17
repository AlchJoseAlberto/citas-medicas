package com.alberto.pacientes.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.alberto.common.controllers.CommonController;
import com.alberto.common.dto.MedicosResponse;
import com.alberto.common.dto.PacienteRequest;
import com.alberto.common.dto.PacienteResponse;
import com.alberto.pacientes.services.PacienteService;

import jakarta.validation.constraints.Positive;

@RestController
public class PacienteController extends CommonController<PacienteRequest, PacienteResponse, PacienteService> {

	public PacienteController(PacienteService service) {
		super(service);
		// TODO Auto-generated constructor stub
	}
	
	@GetMapping("/id-paciente/{id}")
	public ResponseEntity<PacienteResponse> obtenerPacienteSinEstado(@PathVariable @Positive(message = "El ID debe ser positivo") Long id){
	return ResponseEntity.ok(service.obtenerPacienteSinEstadoPorId(id));
}

}
