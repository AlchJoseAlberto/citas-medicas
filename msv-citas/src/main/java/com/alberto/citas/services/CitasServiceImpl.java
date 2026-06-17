package com.alberto.citas.services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alberto.citas.dto.CitasRequest;
import com.alberto.citas.dto.CitasResponse;
import com.alberto.citas.entities.Citas;
import com.alberto.citas.mappers.CitasMapper;
import com.alberto.citas.repositories.CitasRepository;
import com.alberto.common.clients.MedicoClient;
import com.alberto.common.clients.PacienteClient;
import com.alberto.common.dto.MedicosResponse;
import com.alberto.common.dto.PacienteResponse;
import com.alberto.common.enums.DisponibilidadMedico;
import com.alberto.common.enums.EstadoRegistro;
import com.alberto.common.enums.EstadosCitas;
import com.alberto.common.exceptions.EntidadRelacionadaException;
import com.alberto.common.exceptions.RecursoNoEncontradoException;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
@Service
@AllArgsConstructor
@Transactional
@Slf4j

public class CitasServiceImpl implements CitasService{
	private final CitasRepository citasRepository;
	private final CitasMapper citasMapper;
	private final MedicoClient medicoClient;
	private final PacienteClient pacienteClient;
	private final List<EstadosCitas> ESTADOS_INVALIDOS_REGISTROS_ASIGNADOS =
			List.of(EstadosCitas.PENDIENTE,EstadosCitas.CONFIRMADA,EstadosCitas.EN_CURSO);
	
	@Override
	@Transactional(readOnly = true)
	public List<CitasResponse> listar() {
		// TODO Auto-generated method stub
		
		return citasRepository.findByEstadoRegistro(EstadoRegistro.ACTIVO).stream().map(
				entidad-> citasMapper.entidadResponse(entidad, obtenerPacienteSinEstadoPorId(entidad.getIdPaciente()), obtenerMedicoSiEstado(entidad.getIdMedico()))).toList();
	}

	@Override
	public CitasResponse obtenerPorId(Long id) {
		// TODO Auto-generated method stub
		Citas cita = obtenerCitaActivaOException(id);
		return citasMapper.entidadResponse(cita, obtenerPacienteSinEstadoPorId(cita.getIdPaciente()), obtenerMedicoSiEstado(cita.getIdMedico()));
	}

	@Override
	public CitasResponse registrar(CitasRequest request) {
		// TODO Auto-generated method stub
		
		MedicosResponse medico = obtenerMedicoActivo(request.idMedico());
		validarDisponibilidadMedico(medico.idDisponibilidad());
		
		PacienteResponse paciente = obtenerPacienteActivoPorId(request.idPaciente());
		validadPacienteTieneRegistrosAsignados(request.idPaciente());
		
		Citas cita = citasMapper.requestEntidad(request);
		
		citasRepository.save(cita);
		cambiarDisponibilidadMedicoSegunEstadoCita(cita.getIdMedico(), cita.getEstadoCita());
		
		return citasMapper.entidadResponse(cita, paciente, obtenerMedicoSiEstado(cita.getIdMedico()));
		
	}

	@Override
	public CitasResponse actualizar(CitasRequest request, Long id) {
		Citas cita = obtenerCitaActivaOException(id);
		log.info("Eliminando la cita con id {}",id);
		
		PacienteResponse paciente = obtenerPacienteActivoPorId(request.idPaciente());
		if(!cita.getIdPaciente().equals(request.idPaciente()))
			validadPacienteTieneRegistrosAsignados(request.idPaciente());
		
		MedicosResponse medico= obtenerMedicoActivo(request.idMedico());
		
		if(!cita.getIdMedico().equals(request.idMedico())) {
			validarDisponibilidadMedico(medico.idDisponibilidad());
			cambiarDisponibilidadMedicoSegunEstadoCita(cita.getIdMedico(), EstadosCitas.CANCELADA);
		}
		
		cita.actualizar(
				request.idPaciente(),
				request.idMedico(),
				request.fechaCita(),
				request.sintomas());
		
		cambiarDisponibilidadMedicoSegunEstadoCita(cita.getIdMedico(), cita.getEstadoCita());
		
		return citasMapper.entidadResponse(cita, paciente, medico);
		
	}

	@Override
	public void eliminar(Long id) {
		
		Citas cita = obtenerCitaActivaOException(id);
		log.info("Eliminando la cita con id {}",id);
		cita.eliminar();
		
		cambiarDisponibilidadMedicoSegunEstadoCita(cita.getIdMedico(), cita.getEstadoCita());
		
		log.info("Se elimino la cita");
		
	}
	
	@Override
	public void actualizarEstadoCita(Long idCita, Long idEstadoCita) {
		// TODO Auto-generated method stub
		Citas cita = obtenerCitaActivaOException(idCita);
		log.info("Actualizando la cita {}",cita.getId());
		cita.actualizarEstadoCita(EstadosCitas.obtenerEstadoCitasPorCodigo(idEstadoCita));
		
		cambiarDisponibilidadMedicoSegunEstadoCita(cita.getIdMedico(), cita.getEstadoCita());
		
		log.info("Estado de la cita {} actualizado correctamente",cita.getId());
		
	}

	@Override
	public void medicoTieneCitasAsignadas(Long idMedico) {
		// TODO Auto-generated method stub
		log.info("Validando si el medico tiene una cita activa con los estados: {}",ESTADOS_INVALIDOS_REGISTROS_ASIGNADOS);
		boolean tieneCitas = citasRepository.existsByIdMedicoAndEstadoRegistroAndEstadoCitaIn(idMedico,EstadoRegistro.ACTIVO, ESTADOS_INVALIDOS_REGISTROS_ASIGNADOS);
		
		if(tieneCitas)
			throw new EntidadRelacionadaException("No se puede modificar el medico ya que tiene citas con estados "+ ESTADOS_INVALIDOS_REGISTROS_ASIGNADOS);
		
		
	}
	
	
	@Override
	public void pacienteTieneCitasAsignadas(Long idPaciente) {
		// TODO Auto-generated method stub
		log.info("Validando si el paciente tiene una cita activa con los estados: {}",ESTADOS_INVALIDOS_REGISTROS_ASIGNADOS);
		boolean tieneCitas = citasRepository.existsByIdPacienteAndEstadoRegistroAndEstadoCitaIn(idPaciente,EstadoRegistro.ACTIVO, ESTADOS_INVALIDOS_REGISTROS_ASIGNADOS);
		
		if(tieneCitas)
			throw new EntidadRelacionadaException("No se puede modificar el paciente ya que tiene citas con estados "+ ESTADOS_INVALIDOS_REGISTROS_ASIGNADOS);
		
		
	}
	private void cambiarDisponibilidadMedico(Long idMedico, Long idDisponibilidad) {
		log.info("Actualizando disponibilidad del medico con id {} a {}",idMedico, DisponibilidadMedico.obtenerDisponibilidadPorCodigo(idDisponibilidad));
	
		medicoClient.actualizarDisponibilidadMedico(idMedico, idDisponibilidad);
		
	}
	
	private void cambiarDisponibilidadMedicoSegunEstadoCita(Long idMedico, EstadosCitas estadosCita) {
		switch(estadosCita) {
		case PENDIENTE, CONFIRMADA -> 
		cambiarDisponibilidadMedico(idMedico, DisponibilidadMedico.NO_DISPONIBLE.getCodigo());
		
		case EN_CURSO -> 
		cambiarDisponibilidadMedico(idMedico, DisponibilidadMedico.EN_CONSULTA.getCodigo());
		
		case FINALIZADA, CANCELADA -> 
		cambiarDisponibilidadMedico(idMedico, DisponibilidadMedico.DISPONIBLE.getCodigo());
		}
		
		
	}
	
	private Citas obtenerCitaActivaOException(Long id) {
		return citasRepository.findByIdAndEstadoRegistro(id,EstadoRegistro.ACTIVO).orElseThrow(()->new RecursoNoEncontradoException("Cita activa no encontrada con id "+id));
	}
	
	private MedicosResponse obtenerMedicoActivo(Long idMedico) {
		log.info("obteniendo medico activo por id");
		return medicoClient.obtenerMedicoActivoPorId(idMedico);
	}
	
	private MedicosResponse obtenerMedicoSiEstado(Long idMedico) {
		log.info("obteniendo medico activo por id");
		return medicoClient.obtenerMedicoSinEstadoPorId(idMedico);
	}
	
	private void validarDisponibilidadMedico(Long idDisponibilidad) {
		log.info("Validando si el medico se encuenttra en estado Disponible");
		
		if(!DisponibilidadMedico.DISPONIBLE.getCodigo().equals(idDisponibilidad))
			throw new IllegalStateException("El medico no se encuentra en estado disponible");
	}
	
	
	private void validadPacienteTieneRegistrosAsignados(Long idPaciente)
	{
		log.info("Validadndo si el paciente con id {} tiene una cita activa con los estados: {}",idPaciente,ESTADOS_INVALIDOS_REGISTROS_ASIGNADOS);
		if(citasRepository.existsByIdPacienteAndEstadoRegistroAndEstadoCitaIn(idPaciente, EstadoRegistro.ACTIVO,
				ESTADOS_INVALIDOS_REGISTROS_ASIGNADOS))
			throw new EntidadRelacionadaException("No se puede registrar la cita ya que el paciente solo puede tener una cita activa con los estados de: "+
					ESTADOS_INVALIDOS_REGISTROS_ASIGNADOS);
	}
	
	private PacienteResponse obtenerPacienteActivoPorId(Long id) {
		log.info("obteniendo medico activo por id");
		return pacienteClient.obtenerPacienteActivoPorId(id);
	}
	
	private PacienteResponse obtenerPacienteSinEstadoPorId(Long idPaciente) {
		log.info("obteniendo medico sin estado por id");
		return pacienteClient.obtenerPacienteSinEstadoPorId(idPaciente);
	}

	
	
	
	
	
	
}
