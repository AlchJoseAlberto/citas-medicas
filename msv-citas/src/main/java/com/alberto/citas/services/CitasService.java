package com.alberto.citas.services;

import com.alberto.citas.dto.CitasRequest;
import com.alberto.citas.dto.CitasResponse;
import com.alberto.common.services.CrudService;

public interface CitasService extends CrudService<CitasRequest, CitasResponse> {

		void actualizarEstadoCita(Long idCita, Long idEstadoCita);
		void medicoTieneCitasAsignadas(Long idMedico);
		
		void pacienteTieneCitasAsignadas(Long idPaciente);
}
