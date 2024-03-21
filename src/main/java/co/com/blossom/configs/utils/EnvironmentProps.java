package co.com.blossom.configs.utils;

import lombok.AllArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class EnvironmentProps {
    private final Environment env;

    public String getAWSRegion() {
        return env.getProperty("cloud.aws.region.static");
    }
    public String getAWSCognitoUserPoolId() {
        return env.getProperty("cloud.aws.cognito.user-pool-id");
    }
    public String getAWSCognitoClientId() {
        return env.getProperty("cloud.aws.cognito.client-id");
    }
    public String getAWSCognitoSecret() {
        return env.getProperty("cloud.aws.cognito.secret");
    }
    public String getAWSCognitoKidValue() {
        return env.getProperty("cloud.aws.cognito.kid-value");
    }

    public String getSecurityAllowedOrigins() {
        return env.getProperty("security.allowed-origins");
    }
    public String getSecurityAllowedMethods() {
        return env.getProperty("security.allowed-methods");
    }
    public String getSecurityAllowedHeaders() {
        return env.getProperty("security.allowed-headers");
    }
    public String getSecurityAllowedUris() {
        return env.getProperty("security.allowed-uris");
    }
}
