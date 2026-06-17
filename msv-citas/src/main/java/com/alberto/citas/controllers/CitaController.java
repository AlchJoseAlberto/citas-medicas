package com.alberto.citas.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.alberto.citas.dto.CitasRequest;
import com.alberto.citas.dto.CitasResponse;
import com.alberto.citas.services.CitasService;
import com.alberto.common.controllers.CommonController;

import jakarta.validation.constraints.Positive;

@RestController
public class CitaController extends CommonController<CitasRequest, CitasResponse, CitasService> {

	public CitaController(CitasService service) {
		super(service);
		// TODO Auto-generated constructor stub
	}
	
	@PatchMapping("/{idCita}/estado/{idEstado}")
	public ResponseEntity<Void> actualizarEstadoCita(@PathVariable @Positive(message = "El idCita debe ser positivo") Long idCita,
			@PathVariable @Positive(message = "El idEstado debe ser positivo") Long idEstado){
		service.actualizarEstadoCita(idCita, idEstado);
		return ResponseEntity.noContent().build();
	}
	
	
	@GetMapping("/id-medico/{idMedico}/citas-asignadas")
	public ResponseEntity<Void> medicoTieneCitasAsignadas(@PathVariable @Positive(message = "El idMedico debe ser positivo") Long idMedico )
	{
		service.medicoTieneCitasAsignadas(idMedico);
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping("/id-paciente/{idPaciente}/citas-asigandas")
	public ResponseEntity<Void> pacienteTieneCitasAsignadas(@PathVariable @Positive(message = "El idPaciente debe ser positivo") Long idPaciente )
	{
		service.pacienteTieneCitasAsignadas(idPaciente);
		return ResponseEntity.noContent().build();
	}
	
	
}
