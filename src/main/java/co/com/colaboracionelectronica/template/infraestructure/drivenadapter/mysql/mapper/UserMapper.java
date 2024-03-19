package co.com.colaboracionelectronica.template.infraestructure.drivenadapter.mysql.mapper;

import org.mapstruct.Mapper;

import co.com.colaboracionelectronica.template.domain.model.User;
import co.com.colaboracionelectronica.template.infraestructure.drivenadapter.mysql.model.UserEntities;

@Mapper(componentModel = "spring")
public interface UserMapper {

	User convertToEntitiesToModel(UserEntities userEntites);
	
}
