package co.com.blossom.inventorys.orders.domain.model;

import co.com.blossom.masters.products.domain.model.ProductDTO;
import co.com.blossom.masters.users.domain.model.UserDTO;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDetailDTO {

	@PositiveOrZero(message = "{common.field.value.positive}")
	private Integer id;

	@NotNull
	private ProductDTO product;

	private BigDecimal quantity;

	private BigDecimal price;
}
