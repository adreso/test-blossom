package co.com.blossom.masters.users.infraestructure.mysql.gateway;

import co.com.blossom.configs.enumerators.RoleENUM;
import co.com.blossom.masters.users.domain.gateways.UserGateway;
import co.com.blossom.masters.users.domain.model.UserDTO;
import co.com.blossom.masters.users.infraestructure.mysql.UserRepository;
import co.com.blossom.masters.users.infraestructure.mysql.mapper.UserMapper;
import co.com.blossom.masters.users.infraestructure.mysql.model.UserEntity;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserGatewayImpl implements UserGateway {

    @NotNull
    private final UserRepository repository;

    @NotNull
    private final UserMapper userMapper;

    @Override
    public UserDTO createUser(UserDTO userDTO) {
        UserEntity userEntityToCreate = userMapper.mapModelToEntity(userDTO);
        userEntityToCreate.setId(null);
        userEntityToCreate.setActive(true);
        userEntityToCreate.setDeleted(false);
        userEntityToCreate.setRole(RoleENUM.USER.name());
        UserEntity userEntityCreated = repository.save(userEntityToCreate);

        return userMapper.mapEntityToModel(userEntityCreated);
    }

    @Override
    public UserDTO findByUsername(String username) {
        return repository.findByUserName(username)
                .map(userMapper::mapEntityToModel)
                .orElse(null);
    }

}
