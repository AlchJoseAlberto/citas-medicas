package com.alberto.common.dto;

import jakarta.validation.constraints.*;

public record MedicosRequest(
        @NotBlank(message = "El nombre es requerido")
        @Size(min = 1, max = 50, message = "El nombre debe tener de 1 a 50 caracteres")
        String nombre,
        
        @NotBlank(message = "El apellido paterno es requerido")
        @Size(min = 1, max = 50, message = "El apellido paterno debe tener de 1 a 50 caracteres")
        String apellidoPaterno,
        
        @NotBlank(message = "El apellido materno es requerido")
        @Size(min = 1, max = 50, message = "El apellido materno debe tener de 1 a 50 caracteres")
        String apellidoMaterno,
        
        @NotNull(message = "La esdad es requerida")
        @Min(value = 18, message = "La edad minima es 18")
        @Max(value = 100, message = "La edad maxima es de 100")
        Short edad,
        
        @NotBlank(message = "El email es requerido")
        @Size(min = 8, max = 100, message = "El nombre debe tener de 8 a 100 caracteres")
        @Email(message = "El formato de email es invalido")
        String email,
        
        @NotBlank(message = "El telefono es requerido")
        @Pattern(regexp = "^[0-9]{10}$")
        String telefono,
        
        @NotBlank(message = "La cedula profecionales requerido")
        @Size(min = 12, max = 12, message = "la cedula profecionales debe tener 12 caracteres")
        String cedulaProfesional,
        
        @NotNull(message = "El id de especialidad es requerido")
        @Positive(message = "El id debe ser positivo")
        Long idEspecialidad
) {
}
