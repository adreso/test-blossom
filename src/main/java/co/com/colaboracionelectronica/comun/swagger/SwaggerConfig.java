///*
// * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
// * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
// */
//package co.com.colaboracionelectronica.comun.swagger;
//
//import java.util.Collections;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.bind.annotation.RestController;
//import springfox.documentation.builders.PathSelectors;
//import springfox.documentation.builders.RequestHandlerSelectors;
//import springfox.documentation.service.ApiInfo;
//import springfox.documentation.service.Contact;
//import springfox.documentation.spi.DocumentationType;
//import springfox.documentation.spring.web.plugins.Docket;
//import springfox.documentation.swagger2.annotations.EnableSwagger2;
//
//@Configuration
//@EnableSwagger2
//public class SwaggerConfig {
//
//	@Bean
//	public Docket apiDocket() {
//		return new Docket(DocumentationType.SWAGGER_2)
//				.select()
//				//.apis(RequestHandlerSelectors.basePackage("co.com.colaboracionelectronica"))
//				.apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))
//				.paths(PathSelectors.any())
//				.build()
//				.apiInfo(getApiInfo())
//				;
//	}
//
//	private ApiInfo getApiInfo() {
//		return new ApiInfo(
//				"Lista de servicios APIs",
//				"Descripcion de la lista de servicios",
//				"1.0",
//				"http://dispapeles.com",
//				new Contact("Codmind", "http://dispapeles.com", "Contactenos@dispapeles.com"),
//				"LICENSE",
//				"LICENSE URL",
//				Collections.emptyList()
//				);
//	}
//}
