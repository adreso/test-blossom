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
}
