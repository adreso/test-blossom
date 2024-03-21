package co.com.blossom.auth.appuser.domain.services;

import co.com.blossom.auth.appuser.domain.gateways.AppUserGateway;
import co.com.blossom.auth.appuser.domain.model.AppUserCodeDTO;
import co.com.blossom.auth.appuser.domain.model.AppUserDTO;
import co.com.blossom.auth.appuser.domain.model.TokenDTO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class AppUserServiceImpl implements AppUserService {

	private final AppUserGateway gateway;

	@Override
	public AppUserDTO signUp(AppUserDTO appUserDTO) {
		gateway.signUp(appUserDTO);
		return appUserDTO;
	}

	@Override
	public TokenDTO signIn(AppUserDTO appUserDTO) {
		log.info("Sign in: {}", appUserDTO);
		return gateway.signIn(appUserDTO);
	}

	@Override
	public boolean confirmAccount(AppUserCodeDTO appUserCodeDTO) {
		log.info("Confirm account: {} -> {}", appUserCodeDTO.getUsername(), appUserCodeDTO.getCode());
		return gateway.confirmAccount(appUserCodeDTO.getUsername(), appUserCodeDTO.getCode());
	}

}
