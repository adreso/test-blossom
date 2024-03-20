package co.com.blossom.configs;

import co.com.blossom.configs.exceptions.ConstraintFacade;
import co.com.blossom.configs.exceptions.CustomConstraintMessage;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.support.ResourceBundleMessageSource;

/**
 * Define configuraciones que afectan transversalmente el microservicio
 */
@Configuration
public class AppConfig {

    /**
     * Path message file
     * @return Ref to ResourceBundle
     */
    @Bean
    public MessageSource messageSource() {

        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasenames("messages/message");
        messageSource.setUseCodeAsDefaultMessage(true);
        messageSource.setDefaultEncoding("UTF-8");

        return messageSource;
    }

    /**
     * Identify the constraint and return the message
     *
     * @return String with the message or null value
     */
    @Bean
    @Primary
    public ConstraintFacade constraintFacade() {
        return new CustomConstraintMessage(messageSource());
    }

}
