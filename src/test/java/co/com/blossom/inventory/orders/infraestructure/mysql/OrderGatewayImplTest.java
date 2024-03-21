package co.com.blossom.inventory.orders.infraestructure.mysql;

import co.com.blossom.configs.utils.CustomSlice;
import co.com.blossom.inventorys.orders.domain.model.OrderDTO;
import co.com.blossom.inventorys.orders.infraestructure.mysql.OrderRepository;
import co.com.blossom.inventorys.orders.infraestructure.mysql.gateway.OrderGatewayImpl;
import co.com.blossom.inventorys.orders.infraestructure.mysql.mapper.OrderMapper;
import co.com.blossom.inventorys.orders.infraestructure.mysql.model.OrderEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class OrderGatewayImplTest {

    @Mock
    private OrderRepository repository;

    @Mock
    private OrderMapper orderMapper;

    @InjectMocks
    private OrderGatewayImpl orderGateway;

    private OrderEntity orderEntity;

    private OrderDTO orderDTO;

    @BeforeEach
    public void setUp() {
        orderEntity = new OrderEntity();
        orderEntity.setId(1);
        orderEntity.setDateOrder(LocalDateTime.now());
        orderEntity.setDetail(List.of());
        orderEntity.setConfirmed(false);

        orderDTO = OrderDTO.builder()
            .id(1)
            .dateOrder(LocalDateTime.now())
            .detail(List.of())
            .confirmed(false)
            .build();


        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreate() {
        OrderEntity orderEntity2 = new OrderEntity();
        orderEntity2.setId(1);


        when(orderMapper.mapModelToEntity(any(OrderDTO.class))).thenReturn(orderEntity);
        when(repository.save(any(OrderEntity.class))).thenReturn(orderEntity2);

        Integer result = orderGateway.create(orderDTO);

        assertEquals(1, result);
    }

    @Test
    public void testHistoryByUser() {
        String username = "testUser";
        LocalDateTime dateFrom = LocalDateTime.parse("1900-01-01T00:00:00");
        LocalDateTime dateTo = LocalDateTime.parse("2099-12-31T23:59:59");
        Pageable pageable = Pageable.unpaged();
        Page<OrderEntity> page = new PageImpl<>(Collections.emptyList());

        when(repository.findByFilter(username, dateFrom, dateTo, pageable)).thenReturn(page);
        when(orderMapper.mapEntityToModel(any(OrderEntity.class))).thenReturn(new OrderDTO());

        CustomSlice<OrderDTO> result = orderGateway.historyByUser(username, dateFrom, dateTo, pageable);

        assertEquals(0, result.getElements().size());
    }
}