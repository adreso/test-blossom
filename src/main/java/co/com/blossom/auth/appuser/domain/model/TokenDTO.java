package co.com.blossom.auth.appuser.domain.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class TokenDTO {
    private String refreshToken;
    private String accessToken;
    private String tokenType;
    private Integer expiresIn;
}
