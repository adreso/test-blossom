package co.com.blossom.auth.appuser.application.rest;

import co.com.blossom.auth.appuser.domain.model.AppUserCodeDTO;
import co.com.blossom.auth.appuser.domain.model.AppUserDTO;
import co.com.blossom.auth.appuser.domain.services.AppUserService;
import co.com.blossom.configs.annotations.ExceptionsControl;
import co.com.blossom.configs.controller.AbstractRestController;
import co.com.blossom.configs.utils.StandarResponse;
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
@RequestMapping(value = "/v1/auth")
@AllArgsConstructor
@Tag(name = "Auth", description = "Endpoints for auth management")
public class AppUserController extends AbstractRestController {
	private final AppUserService appUserService;

	@Operation(description = "Allow to sign-in a user")
	@PostMapping(value = "/confirm-account", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
	@ExceptionsControl
	public ResponseEntity<StandarResponse> confirmAccount(@Valid @RequestBody AppUserCodeDTO appUserCodeDTO) {
		StandarResponse response = buildSuccessResponseDTO(appUserService.confirmAccount(appUserCodeDTO));
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@Operation(description = "Allow to sign-in a user")
	@PostMapping(value = "/sign-in", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
	@ExceptionsControl
	public ResponseEntity<StandarResponse> signIn(@Valid @RequestBody AppUserDTO appUserDTO) {
		StandarResponse response = buildSuccessResponseDTO(appUserService.signIn(appUserDTO));
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}
