package co.com.blossom.configs.security.services;

import co.com.blossom.configs.security.model.TokenDetail;

public interface JWTProcessor {
    TokenDetail deserializeToken(String token);
}
