package co.com.blossom.configs.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.HashMap;
import java.util.Map;

public class JWTUtils {
    public static Map<String, Object> decodeJWT(String jwtToken) {
        DecodedJWT decodedJWT = JWT.decode(jwtToken);
        Map<String, Object> claims = new HashMap<>();
        decodedJWT.getClaims().forEach((key, value) -> {
            claims.put(key, value.asString());
        });
        return claims;
    }
}
