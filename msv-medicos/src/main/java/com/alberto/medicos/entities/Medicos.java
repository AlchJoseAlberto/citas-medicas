package com.alberto.medicos.entities;

import com.alberto.common.enums.DisponibilidadMedico;
import com.alberto.common.enums.EspecialidadMedico;
import com.alberto.common.enums.EstadoRegistro;
import com.alberto.common.utils.StringCustomUtils;
import com.alberto.common.utils.ValoresNumericosUtils;

import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter @Getter
@Builder
@Table(name = "MEDICOS")
public class Medicos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_MEDICO")
    private Long idMedico;
    @Column(name = "NOMBRE", length =50 ,nullable = false)
    private String nombre;
    @Column(name = "APELLIDO_PATERNO", length =50 ,nullable = false)
    private String apellidoPaterno;
    @Column(name = "APELLIDO_MATERNO", length =50 ,nullable = false)
    private String apellidoMaterno;
    @Column(name = "EDAD", nullable = false)
    private Short edad;
    @Column(name = "EMAIL", length =100 ,nullable = false)
    private String email;
    @Column(name = "TELEFONO", length =10 ,nullable = false)
    private String telefono;
    @Column(name = "CEDULA_PROFESIONAL", length = 12,nullable = false)
    private String cedulaProfesional;
    @Column(name = "ESPECIALIDAD",nullable = false)
    @Enumerated(EnumType.STRING)
    private EspecialidadMedico especialidad;
    @Column(name = "DISPONIBILIDAD" ,nullable = false)
    @Enumerated(EnumType.STRING)
    private DisponibilidadMedico disponibilidad;
    @Column(name = "ESTADO_REGISTRO" ,nullable = false)
    @Enumerated(EnumType.STRING)
    private EstadoRegistro estadoRegistro;
    

			


	public void actualizar(String nombre, String apellidoPaterno, String apellidoMaterno, Short edad, String email,
			String telefono, String cedulaProfesional, EspecialidadMedico especialidad) {
		
		
		validadDatos(nombre,apellidoPaterno,apellidoMaterno,edad,email,telefono,cedulaProfesional,especialidad);
		actualizarEspecialidad(especialidad);
		this.nombre = nombre.trim();
		this.apellidoPaterno = apellidoPaterno.trim();
		this.apellidoMaterno = apellidoMaterno.trim();
		this.edad = edad;
		this.email = email.trim().toLowerCase();
		this.telefono = telefono.trim();
		this.cedulaProfesional = cedulaProfesional.trim();
		
	}
	
	
	public void eliminar(){
		validarNoEliminado();
	this.estadoRegistro= EstadoRegistro.ELIMINADO;
    }
    
	public void actualizarDisponibilidad(DisponibilidadMedico nuevaDisponibidad) {
		validarNoEliminado();
		if(nuevaDisponibidad ==null)
			throw new IllegalArgumentException("La disponibidad es requerida");
		
		this.disponibilidad = nuevaDisponibidad;
	}
	
	public void actualizarEspecialidad(EspecialidadMedico nuevaEspecialidad) {
		validarNoEliminado();
		if(nuevaEspecialidad ==null)
			throw new IllegalArgumentException("La especialidad es requerida");
		
		this.especialidad = nuevaEspecialidad;
	}
	
	
	private void validadDatos(String nombre, String apellidoPaterno, String apellidoMaterno, Short edad, String email,
			String telefono, String cedulaProfesional, EspecialidadMedico especialidad) {
		
		StringCustomUtils.validarTamanio(nombre, 1, 50, "El nombre debe tener de 1 a 50 caracteres");
		StringCustomUtils.validarTamanio(apellidoPaterno, 1, 50, "El apellido paterno debe tener de 1 a 50 caracteres");
		StringCustomUtils.validarTamanio(apellidoMaterno, 1, 50, "El apellido materno debe tener de 1 a 50 caracteres");
		StringCustomUtils.validarTamanio(email, 1, 100, "El email debe tener de 1 a 100 caracteres");
		StringCustomUtils.validarTamanio(telefono, 10, 10, "El telefono debe tener de 10 digitos (0-9)");
		StringCustomUtils.validarTamanio(cedulaProfesional, 12, 12, "La cedula profesional debe tener de 12 caracteres");
		
		ValoresNumericosUtils.validarRangoShort(edad, (short)18, (short)100, "La edad debe ser mayor a los 18 y menor a 100");
		if(especialidad == null)
			throw new IllegalArgumentException("La especialidad es requerida");
	}
	
	
	private void validarNoEliminado() {
		if (this.estadoRegistro == EstadoRegistro.ELIMINADO) {
			throw new IllegalStateException("Ya se encuentra eliminado");
		}
	}
	
	
	
	
	
    
}
