package co.com.colaboracionelectronica.genestados.domain.services;

import co.com.colaboracionelectronica.genestados.domain.gateways.GenEstadosRepository;
import co.com.colaboracionelectronica.genestados.domain.model.GenEstadosDTO;
import co.com.colaboracionelectronica.genestados.domain.model.projections.GenEstadosFullDescripcion;
import co.com.colaboracionelectronica.genestados.domain.model.projections.GenEstadosProjection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GenEstadosServicesImpl implements GenEstadosServices{
    
    @Autowired
    private GenEstadosRepository estadosRepository;

    @Override
    public GenEstadosDTO getGenEstadoByEstado(Integer estado) {
        return estadosRepository.findGenEstadoByEstado(estado);
    }

    @Override
    public GenEstadosProjection getGenEstadoProjectionByEstado(Integer estado) {
        return estadosRepository.findGenEstadoByEstadoProjection(estado);
    }

    @Override
    public GenEstadosDTO setGenEstados(GenEstadosDTO estados) {
        return estadosRepository.saveGenEstados(estados);
    }

    @Override
    public GenEstadosFullDescripcion getEstadoFullDescripcion(Integer estado) {
        return estadosRepository.getEstadoFullDescripcion(estado);
    }
    
    
}
