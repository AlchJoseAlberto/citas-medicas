package com.alberto.servicioa.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient("servicio-b")
public interface MensajeClient {

	@GetMapping("/mensaje")
	String obtenerMensaje();
	
}
