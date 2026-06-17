package com.alberto.common.enums;

import java.util.EnumSet;
import java.util.Set;

import com.alberto.common.exceptions.RecursoNoEncontradoException;

import lombok.AllArgsConstructor;
import lombok.Getter;
@AllArgsConstructor
@Getter
public enum EstadosCitas {

	PENDIENTE(1L,"Pendiente de confirmar",true,true) {
		@Override
		public Set<EstadosCitas> puedeCambiar() {
			return EnumSet.of(CONFIRMADA,CANCELADA);
		}
	},
	CONFIRMADA(2L,"Confirmada por el paciente",true,false) {
		@Override
		public Set<EstadosCitas> puedeCambiar() {
			return EnumSet.of(EN_CURSO,CANCELADA);
		}
	},	
	EN_CURSO(3L,"Paciente llegó", false,false) {
		@Override
		public Set<EstadosCitas> puedeCambiar() {
			return EnumSet.of(FINALIZADA);
		}
	},
	FINALIZADA(4L,"Cita finalizada", false,true) {
		@Override
		public Set<EstadosCitas> puedeCambiar() {
			return Set.of();
		}
	},
	CANCELADA(5L,"Cita cancelada", false,true) {
		@Override
		public Set<EstadosCitas> puedeCambiar() {
			return Set.of();
		}
	};
	
	private final Long codigo;
	
	private final String descripcion;
	
	private final boolean actualizable;
	
	private final boolean eliminable;

	public boolean puedeCambiarA(EstadosCitas nuevoEstado) {
		return this.puedeCambiar().contains(nuevoEstado);
	}
	
	public abstract Set<EstadosCitas> puedeCambiar();
	
	public static EstadosCitas obtenerEstadoCitasPorCodigo(Long codigo) {


        for (EstadosCitas d : values()) {

            if (d.codigo == codigo) {

                return d;
            }
        }

        throw new RecursoNoEncontradoException(
                "No existe el estado de cita con el codigo: "
                        + codigo
        );
    }


}
