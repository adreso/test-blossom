package co.com.blossom.inventorys.orders.domain.model;

import co.com.blossom.masters.users.domain.model.UserDTO;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.catalina.User;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDTO {

	@PositiveOrZero(message = "{common.field.value.positive}")
	private Integer id;

	private UserDTO user;

	private LocalDateTime dateOrder;

	private List<OrderDetailDTO> detail;

	private Boolean confirmed = Boolean.FALSE;
}
