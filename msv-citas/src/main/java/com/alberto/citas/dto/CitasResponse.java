package com.alberto.citas.dto;

import java.time.LocalDateTime;

import com.alberto.common.dto.datos.DatosPaciente;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.alberto.common.dto.datos.DatosMedico;

public record CitasResponse(
		Long id,
		DatosPaciente datospaciente,
		DatosMedico datosmedicos,
		@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyy HH:mm")
		LocalDateTime fechaCita,
		String sintomas,
		String estadoCita) {

}
