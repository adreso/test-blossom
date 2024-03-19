package co.com.colaboracionelectronica.genestados.domain.services;

import co.com.colaboracionelectronica.genestados.domain.model.GenEstadosDTO;
import co.com.colaboracionelectronica.genestados.domain.model.projections.GenEstadosFullDescripcion;
import co.com.colaboracionelectronica.genestados.domain.model.projections.GenEstadosProjection;

public interface GenEstadosServices {

    GenEstadosDTO getGenEstadoByEstado(Integer estado);

    GenEstadosProjection getGenEstadoProjectionByEstado(Integer estado);

    GenEstadosFullDescripcion getEstadoFullDescripcion(Integer estado);

    GenEstadosDTO setGenEstados(GenEstadosDTO estados);
    
}
