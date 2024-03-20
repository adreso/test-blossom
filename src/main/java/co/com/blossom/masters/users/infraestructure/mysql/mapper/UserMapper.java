package co.com.blossom.masters.users.infraestructure.mysql.mapper;

import co.com.blossom.configs.enumerators.RoleENUM;
import co.com.blossom.masters.users.domain.model.UserDTO;
import co.com.blossom.masters.users.infraestructure.mysql.model.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

	UserDTO mapEntityToModel(UserEntity userEntity);
	UserEntity mapModelToEntity(UserDTO userDTO);
}
