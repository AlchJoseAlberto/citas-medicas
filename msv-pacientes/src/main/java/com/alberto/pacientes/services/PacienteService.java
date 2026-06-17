package com.alberto.pacientes.services;

import com.alberto.common.dto.PacienteRequest;
import com.alberto.common.dto.PacienteResponse;
import com.alberto.common.services.CrudService;


public interface PacienteService extends CrudService<PacienteRequest, PacienteResponse> {
	public PacienteResponse obtenerPacienteSinEstadoPorId(Long id);
}
