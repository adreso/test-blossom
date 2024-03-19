package co.com.colaboracionelectronica;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
public class TemplateApplicationRest {

	public static void main(String[] args) {
		SpringApplication.run(TemplateApplicationRest.class, args);
	}

}
