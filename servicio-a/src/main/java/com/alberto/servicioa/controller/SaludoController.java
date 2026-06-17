package com.alberto.servicioa.controller;

import org.springframework.web.bind.annotation.*;

import com.alberto.servicioa.client.MensajeClient;

@RestController
@RequestMapping("/saludo")
public class 	SaludoController {
private final MensajeClient mensajeClient;

public SaludoController(MensajeClient mensajeClient) {
	super();
	this.mensajeClient = mensajeClient;
}



	@GetMapping
	public String saludo() {
		return "Servicio A dice: " + mensajeClient.obtenerMensaje();
	}
	
}
