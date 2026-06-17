package com.alberto.pacientes.entities;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

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
@Table(name = "PACIENTES")
public class Paciente {
	
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
@Column(name = "ID_PACIENTE")
private Long id;

@Column(name = "NOMBRE", length = 50, nullable = false)
private String nombre;
@Column(name = "APELLIDO_PATERNO", length = 50, nullable = false)
private String apellidoPaterno;
@Column(name = "APELLIDO_MATERNO", length = 50, nullable = false)
private String apellidoMaterno;
@Column(name = "EDAD",  nullable = false)
private Short edad;
@Column(name = "PESO", nullable = false)
private Double peso;
@Column(name = "ESTATURA",  nullable = false)
private Double estatura;
@Column(name = "IMC", nullable = false)
private Double imc;

@Column(name = "EMAIL", length = 100, nullable = false)
private String email;

@Column(name = "TELEFONO", length = 10, nullable = false)
private String telefono;

@Column(name = "DIRECCION", length = 150, nullable = false)
private String direccion;

@Column(name = "NUM_EXPEDIENTE", length = 20, nullable = false)
private String numExpediente;

@Enumerated(EnumType.STRING)
@JdbcTypeCode(SqlTypes.NAMED_ENUM)
@Column(name = "ESTADO_REGISTRO", nullable = false)
private EstadoRegistro estadoRegistro;


public void actualizar(String nombre, String apellidoPaterno, String apellidoMaterno,
		Short edad, Double peso, Double estatura, String email,
		String telefono, String direccion) {
	
	validaNoEliminado();
	
	validarDatos(nombre, apellidoPaterno, apellidoMaterno, email, telefono, direccion, edad, peso, estatura);
	

	this.nombre = nombre.trim();
	this.apellidoPaterno = apellidoPaterno.trim();
	this.apellidoMaterno = apellidoMaterno.trim();
	this.edad = edad;
	this.peso = peso;
	this.estatura = estatura;
	this.asigImc();
	this.email = email.trim().toLowerCase();
	this.telefono = telefono.trim();
	this.direccion = direccion.trim();
	this.generarExpediente();
}

public void asigImc() {
	validaNoEliminado();
	if(peso==null || estatura ==null || estatura==0) {
		this.imc=0.0;
		return;
	}
	this.imc = this.peso / (this.estatura * this.estatura);
}


public void generarExpediente() {
	validaNoEliminado();
	
	if(telefono == null) {
		this.numExpediente = null;
	return;
	}
	
	StringBuilder expediente = new StringBuilder(telefono.length()*2);
	
	for(char c : this.telefono.toCharArray())
		expediente.append(c).append("X");
	
	this.numExpediente = expediente.toString();
	
}



private void validaNoEliminado() {
	if(this.estadoRegistro == EstadoRegistro.ELIMINADO)
		throw new IllegalStateException("El paciente esta eliminado");
}

public void eliminar() {
	validaNoEliminado();
	this.estadoRegistro = EstadoRegistro.ELIMINADO;
}


private void validarDatos(String nombre, String apellidoPaterno, String apellidoMaterno, String email, String telefono,
		String direccion, Short edad, Double peso, Double estatura) {

	StringCustomUtils.validarTamanio(nombre, 1, 50, "El nombre es requerido y debe tener de 1 y 50 caracteres");
	StringCustomUtils.validarTamanio(apellidoPaterno, 1, 50, "El apellidoPaterno es requerido y debe tener de 1 y 50 caracteres");
	StringCustomUtils.validarTamanio(apellidoMaterno, 1, 50, "El apellidoMaterno es requerido y debe tener de 1 y 50 caracteres");
	StringCustomUtils.validarTamanio(email, 1, 100, "El email es requerido y debe tener de 1 y 100 caracteres");
	StringCustomUtils.validarTamanio(telefono, 10, 10, "El telefono es requerido y debe tener de 10 caracteres");
	StringCustomUtils.validarTamanio(direccion, 1, 150, "El direccion es requerido y debe tener de 1 y 150 caracteres");
	
	ValoresNumericosUtils.validarRangoShort(edad, (short) 1, (short) 100, "La edad debe ser de 1 a 100 años");
	ValoresNumericosUtils.validarRangoDouble(peso, 1.0, 200.0, "El peso debe ser de 1.0 a 200.0 kilos");
	ValoresNumericosUtils.validarRangoDouble(estatura, 1.0, 2.0, "La estatura debe ser de 1.0 m a 2.0 m");
	
}










}
