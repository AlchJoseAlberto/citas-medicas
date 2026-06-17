package com.alberto.citas.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record CitasRequest(
		@NotNull(message = "El id del paciente no puede estra vacio")
		@Positive(message = "El id de paciente debe ser positivo")
		Long idPaciente,
		@Positive(message = "El id del medico debe ser positivo")
		@NotNull(message = "El id del paciente no puede estra vacio")
		Long idMedico,
		@NotNull(message = "la fecha de la cita es requerida")
		@FutureOrPresent(message = "La fecha debe ser futura")
		@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm")
		LocalDateTime fechaCita,
		
		@NotBlank(message = "Los sintomas son requeridos")
		@Size(min = 20, max = 500,message = "Los sintomas deben ser de 20 a 500 caracteres")
		String sintomas) {

}
