package com.alberto.citas.entities;

import java.time.LocalDateTime;

import com.alberto.common.enums.EstadoRegistro;
import com.alberto.common.enums.EstadosCitas;
import com.alberto.common.utils.StringCustomUtils;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Builder
@Table(name = "CITAS")
@ToString
public class Citas {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_CITA", nullable = false)
	private Long id;
	@Column(name = "ID_PACIENTE", nullable = false)
	private Long idPaciente;
	@Column(name = "ID_MEDICO", nullable = false)
	private Long idMedico;
	@Column(name = "FECHA_CITA", nullable = false)
	private LocalDateTime fechaCita;
	@Column(name = "SINTOMAS", nullable = false)
	private String sintomas;
	@Column(name = "ESTADO_CITA", nullable = false, length = 500)
	@Enumerated(EnumType.STRING)
	private EstadosCitas estadoCita;
	@Column(name = "ESTADO_REGISTRO", nullable = false)
	@Enumerated(EnumType.STRING)
	private EstadoRegistro estadoRegistro;
	
	
	public static Citas crear(Long idPaciente, Long idMedico, LocalDateTime fechaCita, String sintomas) {
		validarId(idPaciente, "paciente");
		validarId(idMedico, "médico");
		
		validaFechaCita(fechaCita);
		
		StringCustomUtils.validarNoVacio(sintomas, "Los sintomas son requeridos");
		
		return Citas.builder()
				.idPaciente(idPaciente)
				.idMedico(idMedico)
				.fechaCita(fechaCita)
				.sintomas(sintomas)
				.estadoCita(EstadosCitas.PENDIENTE)
				.estadoRegistro(EstadoRegistro.ACTIVO)
				.build();
	}
	
	public void eliminar() {
		validaEliminacionPermitido();
		this.estadoRegistro = EstadoRegistro.ELIMINADO;
	}
	
	public void actualizarEstadoCita(EstadosCitas nuevoEstado) {
		validarNoEliminado();
		if(nuevoEstado==null)
			throw new IllegalArgumentException("El nuevo estado de la cita es requerido");
		
		if(!this.estadoCita.puedeCambiarA(nuevoEstado))
			throw new IllegalArgumentException("La cita con estado "+ this.estadoCita + " solo puede cambiar a: "+ this.estadoCita.puedeCambiar());
	
	this.estadoCita = nuevoEstado;
	}
	
	public void actualizar(Long idPaciente, Long idMedico, LocalDateTime fechaCita, String sintomas) {
		validaActualizacionPermitido();
		validarId(idPaciente, "paciente");
		
		validarId(idMedico, "médico");
		
		validaFechaCita(fechaCita);
		
		StringCustomUtils.validarNoVacio(sintomas, "Los sintomas son requeridos");
		this.idPaciente = idPaciente;
		this.idMedico = idMedico;
		this.fechaCita = fechaCita;
		this.sintomas = sintomas;
	}
	
	
	
	private void validarNoEliminado() {
		if(this.estadoRegistro== EstadoRegistro.ELIMINADO)
			throw new IllegalStateException("La cita ya se encuentra eliminada");
	}
	
	private static void validarId(Long id, String campo) {
		if(id==null || id<=0)
			throw new IllegalStateException("El id del del "+campo+" es requerido");
	}
	
	private static void validaFechaCita(LocalDateTime fechacita) {
		if(fechacita==null || !fechacita.isAfter(LocalDateTime.now()))
			throw new IllegalStateException("La fecha de la cita es requerida y debe ser futura");
	}
	
	
	private void validaEliminacionPermitido() {
		validarNoEliminado();
		
		if(!this.estadoCita.isEliminable())
			throw new IllegalStateException("La cita con estado "+ this.estadoCita+ " no puede eliminarse");
	}
	
	
	private void validaActualizacionPermitido() {
		validarNoEliminado();
		
		if(!this.estadoCita.isActualizable())
			throw new IllegalStateException("La cita con estado "+ this.estadoCita+ " no puede actualizarse");
	}



	
	
	
	
	
	
	
	
}
