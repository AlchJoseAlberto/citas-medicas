package com.alberto.medicos.services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alberto.common.clients.CitaClient;
import com.alberto.common.dto.MedicosRequest;
import com.alberto.common.dto.MedicosResponse;
import com.alberto.common.enums.DisponibilidadMedico;
import com.alberto.common.enums.EspecialidadMedico;
import com.alberto.common.enums.EstadoRegistro;
import com.alberto.common.exceptions.RecursoNoEncontradoException;
import com.alberto.medicos.entities.Medicos;
import com.alberto.medicos.mappers.MedicoMapper;
import com.alberto.medicos.repositories.MedicoRepository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Transactional
@Slf4j
public class MedicoServiceImpl  implements MedicoService{
	private final MedicoRepository medicoRepository;
	private final MedicoMapper medicoMapper;
	private final CitaClient citaClient;
	
	@Override
	@Transactional(readOnly = true)
	public List<MedicosResponse> listar() {
		// TODO Auto-generated method stub
		log.info("Listando todos los medicos activos");
		return medicoRepository.findByEstadoRegistro(EstadoRegistro.ACTIVO).stream().map(medicoMapper::entidadResponse).toList();
	}

	@Override
	@Transactional(readOnly = true)
	public MedicosResponse obtenerPorId(Long id) {
		// TODO Auto-generated method stub
		return medicoMapper.entidadResponse(obtenerMedicoActivoPorIdOException(id));
	}
	
	@Override
	@Transactional(readOnly = true)
	public MedicosResponse obtenerMedicoPorIdSinEstado(Long id) {
		// TODO Auto-generated method stub
		log.info("Buscando el medico con sin estado con id {}",id);
		return medicoMapper.entidadResponse(medicoRepository.findById(id)
				.orElseThrow(()->new RecursoNoEncontradoException("Médico activo no encontrado con id "+id)));
	}

	@Override
	public MedicosResponse registrar(MedicosRequest request) {
		// TODO Auto-generated method stub
		log.info("Registrando medico {}",request);
		validarDatosUnicos(request);
		Medicos medicos = medicoMapper.requestEntidad(request);
		
		medicos.actualizarEspecialidad(EspecialidadMedico.obtenerEspecialidadMedicoPorCodigo(request.idEspecialidad()));
		
		medicoRepository.save(medicos);
		log.info("Nuevo medico registrado {}",medicos.getNombre());
		return medicoMapper.entidadResponse(medicos);
	}

	@Override
	public MedicosResponse actualizar(MedicosRequest request, Long id) {
		// TODO Auto-generated method stub
		log.info("Actualizando medico {}",request);
		Medicos medicos = obtenerMedicoActivoPorIdOException(id);
		
		medicoTieneCitasAsignadas(id);
		
		validarCambiosUnicos(request, id);
		
		medicos.actualizar(request.nombre(), request.apellidoPaterno(), request.apellidoMaterno(), request.edad(), request.email(), request.telefono(), request.cedulaProfesional(),
				EspecialidadMedico.obtenerEspecialidadMedicoPorCodigo(request.idEspecialidad()));
		
		log.info("Medico actualizado");
		return medicoMapper.entidadResponse(medicos);
	}
	
	@Override
	public void actualizarDisponibilidadMedico(Long idMedico, Long idDisponibilidad) {
		// TODO Auto-generated method stub
		log.info("Actualizando disponibiliad medico");
		Medicos medicos = obtenerMedicoActivoPorIdOException(idMedico);
		DisponibilidadMedico nuevaDisponibilidadMedico = DisponibilidadMedico.obtenerDisponibilidadPorCodigo(idDisponibilidad);
		DisponibilidadMedico anteriorDisponibilidadMedico = medicos.getDisponibilidad();
		
		if(nuevaDisponibilidadMedico == DisponibilidadMedico.DISPONIBLE)
			medicoTieneCitasAsignadas(idMedico);
		
		
		medicos.actualizarDisponibilidad(nuevaDisponibilidadMedico);
		
		log.info("Disponibidad del medico con id {} cambio de {} a {}",idMedico,anteriorDisponibilidadMedico,nuevaDisponibilidadMedico);
		
		
	}

	@Override
	public void eliminar(Long id) {
		// TODO Auto-generated method stub
		log.info("Eliminando medico {}",id);
		Medicos medicos = obtenerMedicoActivoPorIdOException(id);
		medicoTieneCitasAsignadas(id);
		medicos.eliminar();
		
		log.info("Medico eliminado");
	}
	
	private Medicos obtenerMedicoActivoPorIdOException(Long id) {
		log.info("Buscando el medico con estado {} con id {}",EstadoRegistro.ACTIVO,id);
		return medicoRepository.findByIdMedicoAndEstadoRegistro(id, EstadoRegistro.ACTIVO)
				.orElseThrow(()->new RecursoNoEncontradoException("Médico activo no encontrado con id "+id));
	}


	
	
	private void validarDatosUnicos(MedicosRequest request) {
		log.info("Validando email unico..");
		if(medicoRepository.existsByEmailIgnoreCaseAndEstadoRegistro(request.email().trim(),EstadoRegistro.ACTIVO))
			throw new IllegalArgumentException("Ya existe un medico activo con el email: "+request.email());
		
		log.info("Validando telefono unico..");
		if(medicoRepository.existsByTelefonoAndEstadoRegistro(request.telefono().trim(),EstadoRegistro.ACTIVO))
			throw new IllegalArgumentException("Ya existe un medico activo con el telefono: "+request.telefono());
		
		log.info("Validando cedula procesional unica..");
		if(medicoRepository.existsByCedulaProfesionalAllIgnoreCaseAndEstadoRegistro(request.cedulaProfesional().trim(),EstadoRegistro.ACTIVO))
			throw new IllegalArgumentException("Ya existe un medico activo con la cedula profesional: "+request.cedulaProfesional());
		
	}
	
	
	private void validarCambiosUnicos(MedicosRequest request, Long id) {
		log.info("Validando email unico..");
		if(medicoRepository.existsByEmailIgnoreCaseAndEstadoRegistroAndIdMedicoNot(request.email().trim(),EstadoRegistro.ACTIVO,id))
			throw new IllegalArgumentException("Ya existe un medico activo con el email: "+request.email());
		
		log.info("Validando telefono unico..");
		if(medicoRepository.existsByTelefonoAndEstadoRegistroAndIdMedicoNot(request.telefono().trim(),EstadoRegistro.ACTIVO,id))
			throw new IllegalArgumentException("Ya existe un medico activo con el email: "+request.telefono());
		
		log.info("Validando cedula procesional unica..");
		if(medicoRepository.existsByCedulaProfesionalAllIgnoreCaseAndEstadoRegistroAndIdMedicoNot(request.cedulaProfesional().trim(),EstadoRegistro.ACTIVO,id))
			throw new IllegalArgumentException("Ya existe un medico activo con el email: "+request.cedulaProfesional());
		
	}
	
	
	private void medicoTieneCitasAsignadas(Long idMedico) {
		citaClient.medicoTieneCitasAsignadas(idMedico);
	}
	
	

}
