package co.com.colaboracionelectronica.genestados.domain.gateways;

import co.com.colaboracionelectronica.genestados.domain.model.GenEstadosDTO;
import co.com.colaboracionelectronica.genestados.domain.model.projections.GenEstadosFullDescripcion;
import co.com.colaboracionelectronica.genestados.domain.model.projections.GenEstadosProjection;

public interface GenEstadosRepository {

    GenEstadosDTO findGenEstadoByEstado(Integer estado);

    GenEstadosProjection findGenEstadoByEstadoProjection(Integer estado);

    GenEstadosDTO saveGenEstados(GenEstadosDTO estados);
    
    GenEstadosFullDescripcion getEstadoFullDescripcion(Integer estado);
}
