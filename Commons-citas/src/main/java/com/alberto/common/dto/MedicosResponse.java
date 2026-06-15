package com.alberto.common.dto;

public record MedicosResponse(
        Long id,
        String nombre,
        Short edad,
        String email,
        String telefono,
        String cedulaProfesional,
        String especialidad,
        String disponibilidad,
        Long idDisponibilidad
) {
}
