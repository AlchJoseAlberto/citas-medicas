package com.alberto.medicos.services;

import com.alberto.common.dto.MedicosRequest;
import com.alberto.common.dto.MedicosResponse;
import com.alberto.common.services.CrudService;

public interface MedicoService extends CrudService<MedicosRequest, MedicosResponse> {
	MedicosResponse obtenerMedicoPorIdSinEstado(Long id);
	void actualizarDisponibilidadMedico(Long idMedico, Long idDisponibilidad);
}
