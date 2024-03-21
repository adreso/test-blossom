package blossom.com.co.masters.users.infraestructure.mysql;

import co.com.blossom.configs.enumerators.RoleENUM;
import co.com.blossom.masters.users.domain.model.UserDTO;
import co.com.blossom.masters.users.infraestructure.mysql.UserRepository;
import co.com.blossom.masters.users.infraestructure.mysql.gateway.UserGatewayImpl;
import co.com.blossom.masters.users.infraestructure.mysql.mapper.UserMapper;
import co.com.blossom.masters.users.infraestructure.mysql.model.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserGatewayImplTest {
    @Mock
    private UserRepository repository;
    @Spy
    private UserMapper userMapper = Mappers.getMapper(UserMapper.class);
    @InjectMocks
    private UserGatewayImpl userGateway;

    private UserEntity userEntity;
    private UserDTO userDTO;

    @BeforeEach
    void setUp() {
        userEntity = new UserEntity();
        userEntity.setId(1);
        userEntity.setUserName("user@test.com");
        userEntity.setRole(RoleENUM.GUEST.name());
        userEntity.setDeleted(false);
        userEntity.setActive(true);
        userEntity.setFirstName("User");
        userEntity.setLastName("Test");

        userDTO = UserDTO.builder()
            .id(1)
            .userName("user@test.com")
            .firstName("User")
            .lastName("Test")
            .password("123456")
            .build();
    }

    @Test
    @DisplayName("Create user")
    void create() {
        userDTO.setId(null);

        when(repository.save(any())).thenReturn(userEntity);
        UserDTO userDTOCreated = userGateway.createUser(userDTO);

        assertNotNull(userDTOCreated);
        assertEquals("user@test.com", userDTOCreated.getUserName());
    }

    @Test
    @DisplayName("Find by username")
    void findByUsername() {
        when(repository.findByUserName(any())).thenReturn(Optional.of(userEntity));
        when(userMapper.mapEntityToModel(userEntity)).thenReturn(userDTO);

        UserDTO userDTOFinded = userGateway.findByUsername("user@test.com");
        assertNotNull(userDTOFinded);
        assertEquals("user@test.com", userDTOFinded.getUserName());
    }

}