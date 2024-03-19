package co.com.colaboracionelectronica.template.infraestructure.drivenadapter.mysql.gateway;

import org.springframework.stereotype.Component;

import co.com.colaboracionelectronica.template.domain.gateways.UserRepository;
import co.com.colaboracionelectronica.template.domain.model.User;
import co.com.colaboracionelectronica.template.infraestructure.drivenadapter.mysql.MySqlRepository;
import co.com.colaboracionelectronica.template.infraestructure.drivenadapter.mysql.mapper.UserMapper;
import co.com.colaboracionelectronica.template.infraestructure.drivenadapter.mysql.model.UserEntities;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class UserGatewayImpl implements UserRepository {

	private MySqlRepository repository;
	
	private UserMapper userMapper;
	
	@Override
	public User findUserByIdusuario(String idusuario) {
		UserEntities us = repository.findUserByIdusuario(idusuario);
		return userMapper.convertToEntitiesToModel(us);
	}

}
