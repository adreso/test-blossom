package co.com.blossom.masters.products.application.rest;

import co.com.blossom.configs.annotations.ExceptionsControl;
import co.com.blossom.configs.controller.AbstractRestController;
import co.com.blossom.configs.utils.StandarResponse;
import co.com.blossom.masters.products.domain.model.ProductDTO;
import co.com.blossom.masters.products.domain.services.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping(value = "/v1/products")
@AllArgsConstructor
@Tag(name = "Products", description = "Endpoints for products management")
public class ProductController extends AbstractRestController {
	private final ProductService productService;

	@Operation(description = "Allow to create a product")
	@PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
	@ExceptionsControl
	public ResponseEntity<StandarResponse> create(@Valid @RequestBody ProductDTO productDTO) {
		StandarResponse response = buildSuccessResponseDTO(productService.create(productDTO));
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	@Operation(description = "Allow to update a product")
	@PutMapping(value = "/{id:[0-9]+}", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
	@ExceptionsControl
	public ResponseEntity<StandarResponse> update(@PathVariable Integer id,
												  @Valid @RequestBody ProductDTO productDTO) {
		productDTO.setId(id);
		StandarResponse response = buildSuccessResponseDTO(productService.update(productDTO));
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@Operation(description = "Allow to delete a product by id")
	@DeleteMapping(value = "/{id:[0-9]+}", produces = {MediaType.APPLICATION_JSON_VALUE})
	@ExceptionsControl
	public ResponseEntity<StandarResponse> borrarId(@PathVariable Integer id) {
		productService.delete(id);
		StandarResponse responseDTO = buildSuccessResponseDTO("");
		return new ResponseEntity<>(responseDTO, HttpStatus.OK);
	}

	@Operation(description = "Allow to search a product by id")
	@GetMapping(value = "/{id:[0-9]+}", produces = {MediaType.APPLICATION_JSON_VALUE})
	@ExceptionsControl
	public ResponseEntity<StandarResponse> consultarId(@PathVariable Integer id) {
		StandarResponse responseDTO = buildSuccessResponseDTO(productService.read(id));
		return new ResponseEntity<>(responseDTO, HttpStatus.OK);
	}

	@Operation(description = "Allow to search a product by filter")
	@GetMapping(value = "/list", produces = {MediaType.APPLICATION_JSON_VALUE})
	@ExceptionsControl
	public ResponseEntity<StandarResponse> consultarId(@RequestParam(required = false) String name,
													   @RequestParam(required = false) String category,
													   @RequestParam(required = false) BigDecimal minorPrice,
													   @RequestParam(required = false) BigDecimal majorPrice,
													   @ParameterObject Pageable pagina) {
		StandarResponse responseDTO = buildSuccessResponseDTO(
			productService.findByFilter(name, category, minorPrice, majorPrice, pagina));
		return new ResponseEntity<>(responseDTO, HttpStatus.OK);
	}

}
