package com.alberto.pacientes.services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import com.alberto.common.dto.PacienteRequest;
import com.alberto.common.dto.PacienteResponse;
import com.alberto.common.enums.EstadoRegistro;
import com.alberto.common.exceptions.RecursoNoEncontradoException;
import com.alberto.pacientes.entities.Paciente;
import com.alberto.pacientes.mappers.PacienteMapper;
import com.alberto.pacientes.repositories.PacienteRepository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Transactional
@Slf4j

public class PacienteServiceImpl implements PacienteService {
	private final PacienteRepository pacienteRepository;
	private final PacienteMapper pacienteMapper;
	
	
	
	@Override
	public List<PacienteResponse> listar() {
		// TODO Auto-generated method stub
		return pacienteRepository.findByEstadoRegistro(EstadoRegistro.ACTIVO).stream().map(pacienteMapper::entidadResponse).toList();
	}

	@Override
	public PacienteResponse obtenerPorId(Long id) {
		
		return pacienteMapper.entidadResponse(obtenerPacientePorIdOException(id));
	}
	
		public PacienteResponse obtenerPacienteSinEstadoPorId(Long id) {
				
				return pacienteMapper.entidadResponse(pacienteRepository.findById(id).orElseThrow(
						()-> new RecursoNoEncontradoException("No se encontro el paciente")));
			}

	@Override
	public PacienteResponse registrar(PacienteRequest request) {
		// TODO Auto-generated method stub
		
		if(pacienteRepository.existsByEmailIgnoreCaseAndEstadoRegistro(request.email().trim(),EstadoRegistro.ACTIVO))
			throw new IllegalArgumentException("El email ya esta registrado");
		
		if(pacienteRepository.existsByTelefonoAndEstadoRegistro(request.telefono().trim(), EstadoRegistro.ACTIVO))
			throw new IllegalArgumentException("El telefono ya esta registrado");
	
		
		Paciente paciente = pacienteMapper.requestEntidad(request);
		paciente.asigImc();
		paciente.generarExpediente();
		paciente.setEstadoRegistro(EstadoRegistro.ACTIVO);
		
		pacienteRepository.save(paciente);
			
		return pacienteMapper.entidadResponse(paciente);
	}

	@Override
	public PacienteResponse actualizar(PacienteRequest request, Long id) {
		// TODO Auto-generated method stub
		Paciente paciente = obtenerPacientePorIdOException(id);
		
		if(pacienteRepository.existsByEmailIgnoreCaseAndIdNotAndEstadoRegistro(request.email().trim(), id, EstadoRegistro.ACTIVO))
			throw new IllegalArgumentException("Ya existe un paciente con el email");
		
		if(pacienteRepository.existsByTelefonoAndIdNotAndEstadoRegistro(request.telefono(), id, EstadoRegistro.ACTIVO))
			throw new IllegalArgumentException("Ya existe un paciente con el telefono");
		
		paciente.actualizar(request.nombre(), request.apellidoPaterno(), request.apellidoMaterno(),
				request.edad(), request.peso(), request.estatura(), request.email(), request.telefono(), request.direccion())
		;
		
		return pacienteMapper.entidadResponse(paciente);
	}

	@Override
	public void eliminar(Long id) {
		// TODO Auto-generated method stub
		Paciente paciente = obtenerPacientePorIdOException(id);
		paciente.eliminar();
	}
	
	private Paciente obtenerPacientePorIdOException(Long id) {
		return pacienteRepository.findByIdAndEstadoRegistro(id, EstadoRegistro.ACTIVO).orElseThrow(()-> new RecursoNoEncontradoException(
				"No se encontro el paciente con id "+id));
	}

}
