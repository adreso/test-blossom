package co.com.blossom.masters.users.application.rest;

import co.com.blossom.configs.annotations.ExceptionsControl;
import co.com.blossom.configs.controller.AbstractRestController;
import co.com.blossom.configs.utils.StandarResponse;
import co.com.blossom.masters.users.domain.model.UserDTO;
import co.com.blossom.masters.users.domain.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/v1/users")
@AllArgsConstructor
@Tag(name = "Users", description = "Endpoints for user management")
public class UserController extends AbstractRestController {
	private final UserService userService;

	@Operation(description = "Allow to create a user")
	@PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
	@ExceptionsControl
	public ResponseEntity<StandarResponse> create(@Valid @RequestBody UserDTO userDTO) {
		StandarResponse response = buildSuccessResponseDTO(userService.createUser(userDTO));
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}
}
