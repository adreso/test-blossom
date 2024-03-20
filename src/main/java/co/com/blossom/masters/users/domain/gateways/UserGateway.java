package co.com.blossom.masters.users.domain.gateways;

import co.com.blossom.masters.users.domain.model.UserDTO;

public interface UserGateway {

	UserDTO createUser(UserDTO userDTO);

	UserDTO findByUsername(String username);
}
