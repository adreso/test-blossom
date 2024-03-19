package co.com.colaboracionelectronica.template.infraestructure.rest.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.com.colaboracionelectronica.template.application.services.UserServices;
import co.com.colaboracionelectronica.template.domain.model.User;
import lombok.AllArgsConstructor;



@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class TemplateController {

	private final UserServices userService;
	
	@GetMapping(value = "ping")
	public ResponseEntity<Object> ping() {		
		return new ResponseEntity<>("pong", HttpStatus.OK);
	}
	
	@GetMapping("user/{username}")
	public User userAuth(@PathVariable String username) {	
		return userService.GetUserByUserName(username);		
	}
	
}
