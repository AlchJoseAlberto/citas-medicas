package com.alberto.medicos.mappers;

import org.springframework.stereotype.Component;

import com.alberto.common.dto.MedicosRequest;
import com.alberto.common.dto.MedicosResponse;
import com.alberto.common.enums.DisponibilidadMedico;
import com.alberto.common.enums.EstadoRegistro;
import com.alberto.common.mappers.CommonMapper;
import com.alberto.medicos.entities.Medicos;

@Component
public class MedicoMapper implements CommonMapper<MedicosRequest, MedicosResponse, Medicos> {

	@Override
	public Medicos requestEntidad(MedicosRequest request) {
		// TODO Auto-generated method stub
		 if (request==null)return null;
		    return Medicos.builder().nombre(request.nombre()).apellidoPaterno(request.apellidoPaterno())
		            .apellidoMaterno(request.apellidoMaterno())
		            .edad(request.edad())
		            .email(request.email())
		            .telefono(request.telefono())
		            .cedulaProfesional(request.cedulaProfesional())
		            .disponibilidad(DisponibilidadMedico.DISPONIBLE)
		            .estadoRegistro(EstadoRegistro.ACTIVO)
		            .build();
	}

	@Override
	public MedicosResponse entidadResponse(Medicos entidad) {
		// TODO Auto-generated method stub
		if (entidad==null)return null;

	       return new MedicosResponse(
	               entidad.getIdMedico(),
	               String.join(" ", entidad.getNombre(), entidad.getApellidoPaterno(), entidad.getApellidoMaterno()),
	               entidad.getEdad(),
	               entidad.getEmail(),
	               entidad.getTelefono(),
	               entidad.getCedulaProfesional(),
	               entidad.getEspecialidad().getDescripcion(),
	               entidad.getDisponibilidad().getDescripcion(),
	               entidad.getDisponibilidad().getCodigo());
	}

	
}
