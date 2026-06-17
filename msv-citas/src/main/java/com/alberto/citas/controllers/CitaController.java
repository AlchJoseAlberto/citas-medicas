package com.alberto.citas.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.alberto.citas.dto.CitasRequest;
import com.alberto.citas.dto.CitasResponse;
import com.alberto.citas.services.CitasService;
import com.alberto.common.controllers.CommonController;

@RestController
public class CitaController extends CommonController<CitasRequest, CitasResponse, CitasService> {

	public CitaController(CitasService service) {
		super(service);
		// TODO Auto-generated constructor stub
	}

}
