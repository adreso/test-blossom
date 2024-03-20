package co.com.blossom.inventorys.orders.application.rest;

import co.com.blossom.configs.annotations.ExceptionsControl;
import co.com.blossom.configs.controller.AbstractRestController;
import co.com.blossom.configs.utils.StandarResponse;
import co.com.blossom.inventorys.orders.domain.model.OrderDTO;
import co.com.blossom.inventorys.orders.domain.services.OrderService;
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
import java.util.Date;

@RestController
@RequestMapping(value = "/v1/orders")
@AllArgsConstructor
@Tag(name = "Orders", description = "Endpoints for orders management")
public class OrderController extends AbstractRestController {
	private final OrderService orderService;

	@Operation(description = "Allow to create a order")
	@PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
	@ExceptionsControl
	public ResponseEntity<StandarResponse> create(@Valid @RequestBody OrderDTO orderDTO) {
		StandarResponse response = buildSuccessResponseDTO(orderService.create(orderDTO));
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	@Operation(description = "Allow to get a list of orders by user")
	@GetMapping(value = "/history", produces = {MediaType.APPLICATION_JSON_VALUE})
	@ExceptionsControl
	public ResponseEntity<StandarResponse> historyByUser(@RequestParam String username,
														 @RequestParam(required = false) String dateFrom,
														 @RequestParam(required = false) String dateTo,
														 @ParameterObject Pageable pageable) {
		StandarResponse response = buildSuccessResponseDTO(orderService.findByFilter(username, dateFrom ,dateTo, pageable));
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

}
