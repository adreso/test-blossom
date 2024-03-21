package co.com.blossom.auth.appuser.domain.services;

import co.com.blossom.auth.appuser.domain.model.AppUserCodeDTO;
import co.com.blossom.auth.appuser.domain.model.AppUserDTO;
import co.com.blossom.auth.appuser.domain.model.TokenDTO;

public interface AppUserService {

	AppUserDTO signUp(AppUserDTO appUserDTO);

	TokenDTO signIn(AppUserDTO appUserDTO);

	boolean confirmAccount(AppUserCodeDTO appUserCodeDTO);

}
