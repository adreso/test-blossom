package co.com.colaboracionelectronica.genestados.infraestructure.drivenadapter.mysql.mapper;

import co.com.colaboracionelectronica.genestados.domain.model.GenEstadosDTO;
import co.com.colaboracionelectronica.genestados.infraestructure.drivenadapter.mysql.model.GenEstadosEntity;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface GenEstadosMapper {

    GenEstadosDTO mapGenEstadosEntityToGenEstados(GenEstadosEntity entity);

    GenEstadosEntity mapGenEstadosToGenEstadosEntity(GenEstadosDTO domain);
}
