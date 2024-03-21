package co.com.blossom.inventory.orders.application;

import co.com.blossom.configs.annotations.ExceptionsControl;
import co.com.blossom.configs.enumerators.ProductBrandENUM;
import co.com.blossom.configs.enumerators.ProductCategoryENUM;
import co.com.blossom.configs.utils.CustomSlice;
import co.com.blossom.configs.utils.StandarResponse;
import co.com.blossom.inventorys.orders.application.rest.OrderController;
import co.com.blossom.inventorys.orders.domain.model.OrderDTO;
import co.com.blossom.inventorys.orders.domain.model.OrderDetailDTO;
import co.com.blossom.inventorys.orders.domain.services.OrderService;
import co.com.blossom.masters.products.domain.model.ProductDTO;
import co.com.blossom.masters.users.domain.model.UserDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class OrderControllerTest {
    @Mock
    private OrderService orderService;

    @InjectMocks
    private OrderController orderController;
    private MockMvc mockMvc;

    private OrderDTO order;

    private UserDTO user;
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

        user = UserDTO.builder()
                .id(1)
                .userName("admin")
                .firstName("Admin")
                .lastName("Test")
                .password("123456")
                .build();

        detail = OrderDetailDTO.builder()
                .id(1)
                .product(product)
                .quantity(BigDecimal.valueOf(10))
                .price(BigDecimal.valueOf(100))
                .build();

        order = OrderDTO.builder()
                .id(1)
                .user(user)
                .dateOrder(LocalDateTime.now())
                .detail(List.of(detail))
                .build();

        mockMvc = MockMvcBuilders.standaloneSetup(orderController)
                .setCustomArgumentResolvers(
                        new PageableHandlerMethodArgumentResolver())
                .build();
    }

    @Test
    @DisplayName("Create order")
    public void create() throws Exception {
        when(orderService.create(any())).thenReturn(order.getId());

        mockMvc.perform(post("/v1/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(order)))
                .andExpect(status().isCreated());

    }

    @Test
    @DisplayName("History by user")
    public void historyByUser() throws Exception{
        String username = "admin";
        String dateFrom = "2022-01-01";
        String dateTo = "2022-12-31";

        mockMvc.perform(get("/v1/orders/history")
                        .param("username", username)
                        .param("dateFrom", dateFrom)
                        .param("dateTo", dateTo)
                        .param("page", "1")
                        .param("size", "4"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        ArgumentCaptor<String> usernameCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> dateFromCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> dateToCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<Pageable> pageableCaptor = ArgumentCaptor.forClass(Pageable.class);

        verify(orderService).findByFilter(usernameCaptor.capture(), dateFromCaptor.capture(), dateToCaptor.capture(), pageableCaptor.capture());

        assertEquals(username, usernameCaptor.getValue());
        assertEquals(dateFrom, dateFromCaptor.getValue());
        assertEquals(dateTo, dateToCaptor.getValue());

        Pageable pageable = pageableCaptor.getValue();
        assertTrue(pageable.isPaged());
        assertEquals(1, pageable.getPageNumber());
    }

    static String asJsonString(final Object obj) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            return objectMapper.writeValueAsString(obj);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
