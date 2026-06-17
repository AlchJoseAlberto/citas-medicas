package com.alberto.pacientes.mappers;

import org.springframework.stereotype.Component;

import com.alberto.common.dto.PacienteRequest;
import com.alberto.common.dto.PacienteResponse;
import com.alberto.common.mappers.CommonMapper;
import com.alberto.pacientes.entities.Paciente;

@Component
public class PacienteMapper implements CommonMapper<PacienteRequest, PacienteResponse, Paciente> {

	@Override
	public Paciente requestEntidad(PacienteRequest request) {
		// TODO Auto-generated method stub
		return Paciente.builder()
				.nombre(request.nombre())
				.apellidoPaterno(request.apellidoPaterno())
				.apellidoMaterno(request.apellidoMaterno())
				.edad(request.edad())
				.peso(request.peso())
				.estatura(request.estatura())
				.email(request.email())
				.telefono(request.telefono())
				.direccion(request.direccion())
				.build();
	}

	@Override
	public PacienteResponse entidadResponse(Paciente entidad) {
		// TODO Auto-generated method stub
		return new PacienteResponse(entidad.getId(), String.join(" ", entidad.getNombre(), entidad.getApellidoPaterno(), entidad.getApellidoMaterno()), entidad.getEdad(), entidad.getPeso(),
				entidad.getEstatura(), entidad.getImc(), entidad.getEmail(), entidad.getTelefono(), entidad.getDireccion(), entidad.getNumExpediente());
	}

}
