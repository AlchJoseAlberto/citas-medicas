package com.alberto.citas.mappers;

import org.springframework.stereotype.Component;

import com.alberto.citas.dto.CitasRequest;
import com.alberto.citas.dto.CitasResponse;
import com.alberto.citas.entities.Citas;
import com.alberto.common.dto.MedicosResponse;
import com.alberto.common.dto.PacienteResponse;
import com.alberto.common.dto.datos.DatosMedico;
import com.alberto.common.dto.datos.DatosPaciente;
import com.alberto.common.mappers.CommonMapper;

@Component
public class CitasMapper implements CommonMapper<CitasRequest, CitasResponse, Citas> {

	@Override
	public Citas requestEntidad(CitasRequest request) {
		// TODO Auto-generated method stub
		if (request == null ) return null;
		
		return Citas.crear(request.idPaciente(), request.idMedico(), request.fechaCita(), request.sintomas().trim());
	}

	@Override
	public CitasResponse entidadResponse(Citas entidad) {
		
		return new CitasResponse(entidad.getId(), null, null, entidad.getFechaCita(), entidad.getSintomas(), entidad.getEstadoCita().getDescripcion());

	}
	
	public CitasResponse entidadResponse(Citas entidad,
            PacienteResponse paciente,
            MedicosResponse medico) {
		
		if (entidad == null) {
            return null;
        }

        return new CitasResponse(
                entidad.getId(),
                pacienteResponseADatosPaciente(paciente),
                medicoResponseADatosMedico(medico),
                entidad.getFechaCita(),
                entidad.getSintomas(),
                entidad.getEstadoCita().getDescripcion()
        );
        
		
	}
	
	public DatosMedico medicoResponseADatosMedico(MedicosResponse response) {
		if(response == null) return null;
		
		return new DatosMedico(response.nombre(), response.cedulaProfesional(), response.especialidad());
	}
	
	public DatosPaciente pacienteResponseADatosPaciente(PacienteResponse response) {
		if(response == null) return null;
		
		String edad = response.edad()+" años.";
		String peso = response.peso()+" kg.";
		String estatura = response.estatura()+" m.";
		
		return new DatosPaciente(response.nombre(), response.numExpediente(), edad, peso, estatura,
				response.imc().toString(), response.telefono());
	}
	
}
