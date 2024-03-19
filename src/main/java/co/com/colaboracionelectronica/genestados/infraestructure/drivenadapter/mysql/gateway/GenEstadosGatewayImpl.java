package co.com.colaboracionelectronica.genestados.infraestructure.drivenadapter.mysql.gateway;

import co.com.colaboracionelectronica.genestados.domain.gateways.GenEstadosRepository;
import co.com.colaboracionelectronica.genestados.domain.model.GenEstadosDTO;
import co.com.colaboracionelectronica.genestados.domain.model.projections.GenEstadosFullDescripcion;
import co.com.colaboracionelectronica.genestados.domain.model.projections.GenEstadosProjection;
import co.com.colaboracionelectronica.genestados.infraestructure.drivenadapter.mysql.MySqlGenEstadosRepository;
import co.com.colaboracionelectronica.genestados.infraestructure.drivenadapter.mysql.mapper.GenEstadosMapper;
import co.com.colaboracionelectronica.genestados.infraestructure.drivenadapter.mysql.model.GenEstadosEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class GenEstadosGatewayImpl implements GenEstadosRepository {

    @Autowired
    private MySqlGenEstadosRepository repository;
    
    @Autowired
    private GenEstadosMapper mapper;

    @Override
    public GenEstadosDTO findGenEstadoByEstado(Integer estado) {
        Optional<GenEstadosEntity> entity = repository.findByEstado(estado);
        return mapper.mapGenEstadosEntityToGenEstados(entity.orElse(null));
    }

    @Override
    public GenEstadosProjection findGenEstadoByEstadoProjection(Integer estado) {
        return repository.findByEstadoProjection(estado).orElse(null);
    }

    @Override
    public GenEstadosDTO saveGenEstados(GenEstadosDTO estados) {

        Optional<GenEstadosEntity> existe = repository.findByEstado(estados.getEstado());
        if (existe.isPresent()) {
            throw new RuntimeException("El id " + estados.getEstado() + " ya existe!");
        }

        GenEstadosEntity entity = mapper.mapGenEstadosToGenEstadosEntity(estados);
        GenEstadosEntity entitySaved = repository.save(entity);

        return mapper.mapGenEstadosEntityToGenEstados(entitySaved);
    }

    @Override
    public GenEstadosFullDescripcion getEstadoFullDescripcion(Integer estado) {
        return repository.findEstadoDescripcionByEstado(estado);
    }
}
