package com.alberto.common.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record PacienteRequest(
		
		@NotBlank(message = "El nombre es obligatorio.")
		@Size(min = 1, max = 50, message = "El nombre debe tener entre 1 y 50 caracteres.")
		String nombre,

		@NotBlank(message = "El apellido paterno es obligatorio.")
		@Size(min = 1, max = 50, message = "El apellido paterno debe tener entre 1 y 50 caracteres.")
		String apellidoPaterno,

		@NotBlank(message = "El apellido materno es obligatorio.")
		@Size(min = 1, max = 50, message = "El apellido materno debe tener entre 1 y 50 caracteres.")
		String apellidoMaterno,

		@NotNull(message = "La edad es obligatoria.")
		@Min(value = 1, message = "La edad mínima permitida es 1 año.")
		@Max(value = 100, message = "La edad máxima permitida es 100 años.")
		Short edad,

		@NotNull(message = "El peso es obligatorio.")
		@DecimalMin(value = "1.0", message = "El peso mínimo permitido es 1 kg.")
		@DecimalMax(value = "200.0", message = "El peso máximo permitido es 200 kg.")
		Double peso,

		@NotNull(message = "La estatura es obligatoria.")
		@DecimalMin(value = "1", message = "La estatura mínima permitida es 1 m.")
		@DecimalMax(value = "2", message = "La estatura máxima permitida es 2 m.")
		Double estatura,

		@NotBlank(message = "El correo electrónico es obligatorio.")
		@Size(max = 100, message = "El correo electrónico no puede exceder 100 caracteres.")
		@Email(message = "Debe proporcionar un correo electrónico válido.")
		String email,

		@NotBlank(message = "El teléfono es obligatorio.")
		@Size(min = 10, max = 10, message = "El teléfono debe contener exactamente 10 dígitos.")
		@Pattern(regexp = "^[0-9]{10}$", message = "El teléfono solo debe contener números.")
		String telefono,

		@NotBlank(message = "La dirección es obligatoria.")
		@Size(min = 1, max = 150, message = "La dirección debe tener entre 1 y 150 caracteres.")
		String direccion

		
		
		
		) {

}
