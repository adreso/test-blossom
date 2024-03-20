package co.com.blossom.inventorys.orders.infraestructure.mysql.gateway;

import co.com.blossom.configs.utils.CustomSlice;
import co.com.blossom.inventorys.orders.domain.gateways.OrderGateway;
import co.com.blossom.inventorys.orders.domain.model.OrderDTO;
import co.com.blossom.inventorys.orders.infraestructure.mysql.OrderRepository;
import co.com.blossom.inventorys.orders.infraestructure.mysql.mapper.OrderMapper;
import co.com.blossom.inventorys.orders.infraestructure.mysql.model.OrderEntity;
import co.com.blossom.masters.products.domain.model.ProductDTO;
import co.com.blossom.masters.products.infraestructure.mysql.model.ProductEntity;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Repository
@RequiredArgsConstructor
public class OrderGatewayImpl implements OrderGateway {

    @NotNull
    private final OrderRepository repository;
    @NotNull
    private final OrderMapper orderMapper;

    @Override
    public Integer create(OrderDTO orderDTO) {
        OrderEntity orderEntityToCreate = orderMapper.mapModelToEntity(orderDTO);
        orderEntityToCreate.setId(null);
        OrderEntity orderEntityCreated = repository.save(orderEntityToCreate);

        return orderEntityCreated.getId();
    }

    @Override
    public CustomSlice<OrderDTO> historyByUser(String username, LocalDateTime dateFrom, LocalDateTime dateTo, Pageable pageable) {

        dateFrom = dateFrom == null ?  LocalDateTime.parse("1900-01-01T00:00:00") : dateFrom;
        dateTo = dateTo == null ?  LocalDateTime.parse("2099-12-31T23:59:59") : dateTo;

        Page<OrderEntity> page = repository.findByFilter(username, dateFrom, dateTo, pageable);

        return CustomSlice.<OrderDTO>builder().
            elements(page.map(orderMapper::mapEntityToModel).stream().toList())
            .page(page)
            .build();
    }


}
