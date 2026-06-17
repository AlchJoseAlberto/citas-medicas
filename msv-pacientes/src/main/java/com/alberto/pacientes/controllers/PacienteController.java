package com.alberto.pacientes.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.alberto.common.controllers.CommonController;
import com.alberto.common.dto.PacienteRequest;
import com.alberto.common.dto.PacienteResponse;
import com.alberto.pacientes.services.PacienteService;

@RestController
public class PacienteController extends CommonController<PacienteRequest, PacienteResponse, PacienteService> {

	public PacienteController(PacienteService service) {
		super(service);
		// TODO Auto-generated constructor stub
	}

}
