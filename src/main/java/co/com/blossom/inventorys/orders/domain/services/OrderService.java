package co.com.blossom.inventorys.orders.domain.services;

import co.com.blossom.configs.utils.CustomSlice;
import co.com.blossom.inventorys.orders.domain.model.OrderDTO;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

public interface OrderService {

	Integer create(OrderDTO orderDTO);

	CustomSlice<OrderDTO> findByFilter(String username, String dateFrom, String dateTo, Pageable pageable);

}
