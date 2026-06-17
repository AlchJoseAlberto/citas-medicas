package com.alberto.pacientes.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.alberto.pacientes.entities.Paciente;
import java.util.List;
import java.util.Optional;

import com.alberto.common.enums.EstadoRegistro;


public interface PacienteRepository extends JpaRepository<Paciente, Long> {

	List<Paciente> findByEstadoRegistro(EstadoRegistro estadoRegistro);
	
	Optional<Paciente> findByIdAndEstadoRegistro(Long id, EstadoRegistro estadoRegistro);
	
	boolean existsByEmailIgnoreCaseAndEstadoRegistro(String email, EstadoRegistro estadoRegistro);
	
	boolean existsByEmailIgnoreCaseAndIdNotAndEstadoRegistro(String email, Long id, EstadoRegistro estadoRegistro);
	
	boolean existsByTelefonoAndEstadoRegistro(String telefono, EstadoRegistro estadoRegistro);
	
	boolean existsByTelefonoAndIdNotAndEstadoRegistro(String telefono, Long id, EstadoRegistro estadoRegistro);
	
}
