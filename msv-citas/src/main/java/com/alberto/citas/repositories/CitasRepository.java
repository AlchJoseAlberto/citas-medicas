package com.alberto.citas.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.alberto.citas.entities.Citas;
import com.alberto.common.enums.EstadosCitas;
import java.util.List;
import java.util.Optional;

import com.alberto.common.enums.EstadoRegistro;

public interface CitasRepository extends JpaRepository<Citas, Long> {
	boolean existsByIdAndEstadoCitaNot(Long id, EstadosCitas estadoCita);
	List<Citas> findByEstadoRegistro(EstadoRegistro estadoRegistro);
	
	Optional<Citas> findByIdAndEstadoRegistro(Long id, EstadoRegistro estadoRegistro);
	
	boolean existsByIdPacienteAndEstadoRegistroAndEstadoCitaIn(Long idPaciente, EstadoRegistro estadoRegistro, List<EstadosCitas> estadosCitas);
	boolean existsByIdMedicoAndEstadoRegistroAndEstadoCitaIn(Long idMedico, EstadoRegistro estadoRegistro, List<EstadosCitas> estadosCitas);
}
