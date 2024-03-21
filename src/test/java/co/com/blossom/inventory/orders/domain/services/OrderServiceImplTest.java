package co.com.blossom.inventory.orders.domain.services;

import co.com.blossom.configs.enumerators.ProductBrandENUM;
import co.com.blossom.configs.enumerators.ProductCategoryENUM;
import co.com.blossom.configs.exceptions.DomainException;
import co.com.blossom.configs.utils.CustomSlice;
import co.com.blossom.configs.utils.ErrorCode;
import co.com.blossom.inventorys.orders.domain.gateways.OrderGateway;
import co.com.blossom.inventorys.orders.domain.model.OrderDTO;
import co.com.blossom.inventorys.orders.domain.model.OrderDetailDTO;
import co.com.blossom.inventorys.orders.domain.services.OrderServiceImpl;
import co.com.blossom.masters.products.domain.gateways.ProductGateway;
import co.com.blossom.masters.products.domain.model.ProductDTO;
import co.com.blossom.masters.users.domain.model.UserDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.Collections;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {

    @Mock
    private OrderGateway orderGateway;

    @Mock
    private ProductGateway productGateway;

    @Mock
    private MessageSource messageSource;

    @InjectMocks
    private OrderServiceImpl orderService;

    private OrderDTO orderDTO;

    private OrderDetailDTO detail;

    private ProductDTO product;

    @BeforeEach
    void setUp() {
        product = ProductDTO.builder()
                .id(1)
                .code("123")
                .name("Product Name")
                .category(ProductCategoryENUM.CATEGORY_A.name())
                .brand(ProductBrandENUM.BRAND_A.name())
                .price(BigDecimal.valueOf(10))
                .active(true)
                .build();
        detail = OrderDetailDTO.builder()
                .id(1)
                .product(product)
                .quantity(BigDecimal.valueOf(10))
                .price(BigDecimal.valueOf(100))
                .build();

        orderDTO = OrderDTO.builder()
                .id(1)
                .user(UserDTO.builder().id(1).build())
                .dateOrder(LocalDateTime.now())
                .confirmed(Boolean.FALSE)
                .detail(Collections.singletonList(detail))
                .build();
    }

    @Test
    @DisplayName("Order create")
    void create() {

        when(orderGateway.create(any())).thenReturn(1);
        when(productGateway.existAllProductos(any())).thenReturn(true);

        Integer orderId = orderService.create(orderDTO);

        assertEquals(1, orderId);
    }

    @Test
    @DisplayName("Order find by filter")
    void findByFilter() {
        String username = "testUser";
        String dateFrom = "2022-12-31T23:59:59";
        String dateTo = "2022-12-31T23:59:59";
        Pageable pageable = Pageable.ofSize(10).withPage(0);
        Page<OrderDTO> page = Page.empty();

        when(orderGateway.historyByUser(any(), any(), any(), any())).thenReturn(new CustomSlice<>(Collections.emptyList(), page));

        CustomSlice<OrderDTO> result = orderService.findByFilter(username, dateFrom, dateTo, pageable);

        assertEquals(0, result.getElements().size());
    }

    @Test
    @DisplayName("Order create, order detail is null")
    void createOrderDetailNull() {
        orderDTO.setDetail(null);

        when(messageSource.getMessage(anyString(), any(), any())).thenReturn("order.detail.not.found");

        Exception exception = assertThrows(DomainException.class, () -> orderService.create(orderDTO));
        assertTrue(exception.getMessage().contains("order.detail.not.found"));
        assertEquals(ErrorCode.DOMAIN_RESOURCE_NOT_FOUND, ((DomainException) exception).getCode());
    }

    @Test
    @DisplayName("Order create, order detail is empty")
    void createOrderDetailEmpty() {
        orderDTO.setDetail(Collections.emptyList());

        when(messageSource.getMessage(anyString(), any(), any())).thenReturn("order.detail.not.found");

        Exception exception = assertThrows(DomainException.class, () -> orderService.create(orderDTO));
        assertTrue(exception.getMessage().contains("order.detail.not.found"));
        assertEquals(ErrorCode.DOMAIN_RESOURCE_NOT_FOUND, ((DomainException) exception).getCode());
    }

    @Test
    @DisplayName("Order create, product does not exist")
    void createProductNotExist() {
        when(productGateway.existAllProductos(any())).thenReturn(false);
        when(messageSource.getMessage(anyString(), any(), any())).thenReturn("order.product.not.found");

        Exception exception = assertThrows(DomainException.class, () -> orderService.create(orderDTO));
        assertTrue(exception.getMessage().contains("order.product.not.found"));
        assertEquals(ErrorCode.DOMAIN_RESOURCE_NOT_FOUND, ((DomainException) exception).getCode());
    }

    @Test
    @DisplayName("Order find by filter, dateFrom and dateTo are null")
    void findByFilterDateNull() {
        String username = "testUser";
        Pageable pageable = Pageable.ofSize(10).withPage(0);
        Page<OrderDTO> page = Page.empty();

        when(orderGateway.historyByUser(any(), any(), any(), any())).thenReturn(new CustomSlice<>(Collections.emptyList(), page));

        CustomSlice<OrderDTO> result = orderService.findByFilter(username, null, null, pageable);

        assertEquals(0, result.getElements().size());
    }

    @Test
    @DisplayName("Order find by filter, dateFrom and dateTo are not in correct format")
    void findByFilterDateIncorrectFormat() {
        String username = "testUser";
        String dateFrom = "01-01-2022";
        String dateTo = "31-12-2022";
        Pageable pageable = Pageable.ofSize(10).withPage(0);

        assertThrows(DateTimeParseException.class, () -> orderService.findByFilter(username, dateFrom, dateTo, pageable));
    }


}