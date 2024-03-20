package co.com.blossom.inventorys.orders.domain.services;

import co.com.blossom.configs.exceptions.DomainException;
import co.com.blossom.configs.utils.CustomSlice;
import co.com.blossom.configs.utils.ErrorCode;
import co.com.blossom.inventorys.orders.domain.gateways.OrderGateway;
import co.com.blossom.inventorys.orders.domain.model.OrderDTO;
import co.com.blossom.masters.products.domain.gateways.ProductGateway;
import co.com.blossom.masters.users.domain.model.UserDTO;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService {

	private final OrderGateway gateway;
	private final ProductGateway productGateway;
	private final MessageSource messageSource;

	@Override
	@Transactional
	public Integer create(OrderDTO orderDTO) {
		log.info("Creating order {}", orderDTO);
		orderDTO.setUser(UserDTO.builder().id(1).build());
		orderDTO.setDateOrder(LocalDateTime.now());
		orderDTO.setConfirmed(Boolean.FALSE);

		validateDataOrder(orderDTO);

		return gateway.create(orderDTO);
	}

	@Override
	public CustomSlice<OrderDTO> findByFilter(String username, String dateFrom, String dateTo, Pageable pageable) {
		LocalDateTime dateFromParsed = Objects.nonNull(dateFrom) ? LocalDateTime.parse(dateFrom) : null;
		LocalDateTime dateToParsed = Objects.nonNull(dateFrom) ? LocalDateTime.parse(dateFrom) : null;
		return gateway.historyByUser(username, dateFromParsed, dateToParsed, pageable);
	}

	private void validateDataOrder(OrderDTO orderDTO) {
		if (Objects.isNull(orderDTO.getDetail()) || orderDTO.getDetail().isEmpty()) {
			throw new DomainException(messageSource.getMessage("order.detail.not.found", null,
				Locale.getDefault()), ErrorCode.DOMAIN_RESOURCE_NOT_FOUND);
		}

		Set<Integer> idsProducts = orderDTO.getDetail().stream()
			.map(detail -> detail.getProduct().getId()).collect(Collectors.toSet());

		if (!productGateway.existAllProductos(idsProducts)) {
			throw new DomainException(messageSource.getMessage("order.product.not.found", null,
				Locale.getDefault()), ErrorCode.DOMAIN_RESOURCE_NOT_FOUND);
		}
	}
}
