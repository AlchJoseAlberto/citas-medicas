package com.alberto.medicos.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alberto.common.controllers.CommonController;
import com.alberto.common.dto.MedicosRequest;
import com.alberto.common.dto.MedicosResponse;
import com.alberto.medicos.services.MedicoService;

import jakarta.validation.constraints.Positive;

@RestController
@Validated
public class MedicoController extends CommonController<MedicosRequest, MedicosResponse, MedicoService> {

	public MedicoController(MedicoService service) {
		super(service);
		// TODO Auto-generated constructor stub
	}
	
	@GetMapping("/id-medico/{id}")
		public ResponseEntity<MedicosResponse> obtenerMedicoSinEstado(@PathVariable @Positive(message = "El ID debe ser positivo") Long id){
		return ResponseEntity.ok(service.obtenerMedicoPorIdSinEstado(id));
	}
	
	@PutMapping("/{idMedico}/disponibilidad/{idDisponibilidad}")
	public ResponseEntity<Void> actualizarDisponibilidadMedico(
			@PathVariable @Positive(message = "El idMedico debe ser positivo") Long idMedico,
			@PathVariable @Positive(message = "El idDisponibilidad debe ser positivo") Long idDisponibilidad){
		
		service.actualizarDisponibilidadMedico(idMedico, idDisponibilidad);
		return ResponseEntity.noContent().build();
	}

}
