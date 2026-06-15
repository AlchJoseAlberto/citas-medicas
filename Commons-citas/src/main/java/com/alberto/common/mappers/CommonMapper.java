package com.alberto.common.mappers;


public interface CommonMapper<RQ, RS, E>{

    E requestEntidad(RQ request);
    RS entidadResponse(E entidad);


}
