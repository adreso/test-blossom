package co.com.blossom.auth.appuser.domain.gateways;

import co.com.blossom.auth.appuser.domain.model.AppUserDTO;
import co.com.blossom.auth.appuser.domain.model.TokenDTO;

public interface AppUserGateway {
    void signUp(AppUserDTO appUserDTO);

    TokenDTO signIn(AppUserDTO appUserDTO);

    boolean confirmAccount(String username, String code);
}
