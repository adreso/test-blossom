package co.com.blossom.configs.security.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Builder
@Getter
@Setter
public class TokenDetail {
    private String nameUser;
    private String role;
}
