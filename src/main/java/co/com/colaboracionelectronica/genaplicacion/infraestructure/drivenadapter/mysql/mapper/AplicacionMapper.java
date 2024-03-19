package co.com.colaboracionelectronica.genaplicacion.infraestructure.drivenadapter.mysql.mapper;

import co.com.colaboracionelectronica.genaplicacion.domain.model.Aplicacion;
import co.com.colaboracionelectronica.genaplicacion.infraestructure.drivenadapter.mysql.model.GenAplicacionEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AplicacionMapper {

    Aplicacion convertToEntitiesToModel(GenAplicacionEntity aplicacionEntity);
    GenAplicacionEntity convertToModelToEntities(Aplicacion aplicacion);
}
