package com.alberto.medicos.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.alberto.medicos.entities.Medicos;
import com.alberto.common.enums.EstadoRegistro;
import java.util.List;
import java.util.Optional;



public interface MedicoRepository extends JpaRepository<Medicos, Long> {
	
	List<Medicos> findByEstadoRegistro(EstadoRegistro estadoRegistro);
	
	Optional<Medicos> findByIdMedicoAndEstadoRegistro(Long idMedico, EstadoRegistro estadoRegistro);
	
	boolean existsByEmailIgnoreCaseAndEstadoRegistro(String email, EstadoRegistro estadoRegistro);
	
	boolean existsByTelefonoAndEstadoRegistro(String telefono, EstadoRegistro estadoRegistro);
	
	boolean existsByCedulaProfesionalAllIgnoreCaseAndEstadoRegistro(String cedulaProfesional, EstadoRegistro estadoRegistro);
	
	boolean existsByEmailIgnoreCaseAndEstadoRegistroAndIdMedicoNot(String email, EstadoRegistro estadoRegistro, Long id);
	
	boolean existsByTelefonoAndEstadoRegistroAndIdMedicoNot(String telefono, EstadoRegistro estadoRegistro, Long id);
	
	boolean existsByCedulaProfesionalAllIgnoreCaseAndEstadoRegistroAndIdMedicoNot(String cedulaProfesional, EstadoRegistro estadoRegistro, Long id);

}
