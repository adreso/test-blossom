package co.com.blossom.masters.users.domain.services;

import co.com.blossom.configs.utils.ErrorCode;
import co.com.blossom.configs.exceptions.DomainException;
import co.com.blossom.masters.users.domain.gateways.UserGateway;
import co.com.blossom.masters.users.domain.model.UserDTO;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.Locale;
import java.util.Objects;

@Service
@AllArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

	private final UserGateway gateway;
	private final MessageSource messageSource;

	@Override
	@Transactional
	public UserDTO createUser(UserDTO userDTO) {
		log.info("Registering user {}", userDTO);

		if(Objects.nonNull(gateway.findByUsername(userDTO.getUserName()))) {
			throw new DomainException(messageSource.getMessage("common.record.duplicated",
					new Object[] {userDTO.getUserName()}, Locale.getDefault()), ErrorCode.DOMAIN_RESOURCE_DUPLICATE);
		}

		UserDTO userCrete = gateway.createUser(userDTO);

		return userCrete;
	}

}
