package co.com.blossom.inventorys.orders.domain.gateways;

import co.com.blossom.configs.utils.CustomSlice;
import co.com.blossom.inventorys.orders.domain.model.OrderDTO;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

public interface OrderGateway {

	Integer create(OrderDTO orderDTO);

	CustomSlice<OrderDTO> historyByUser(String username, LocalDateTime dateFrom, LocalDateTime dateTo, Pageable pageable);
}
