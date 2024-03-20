package co.com.blossom.inventory.domain.services;

import co.com.blossom.configs.enumerators.ProductBrandENUM;
import co.com.blossom.configs.enumerators.ProductCategoryENUM;
import co.com.blossom.configs.exceptions.DomainException;
import co.com.blossom.configs.utils.ErrorCode;
import co.com.blossom.masters.products.domain.gateways.ProductGateway;
import co.com.blossom.masters.products.domain.model.ProductDTO;
import co.com.blossom.masters.products.domain.services.ProductServiceImpl;
import co.com.blossom.masters.users.domain.model.UserDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;

import java.math.BigDecimal;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @Mock
    private ProductGateway gateway;

    @InjectMocks
    private ProductServiceImpl service;
    @Mock
    private MessageSource messageSource;

    private ProductDTO dto;

    @BeforeEach
    void setUp() {
        dto = ProductDTO.builder()
            .id(1)
            .code("123")
            .name("Product Name")
            .category(ProductCategoryENUM.CATEGORY_A.name())
            .brand(ProductBrandENUM.BRAND_A.name())
            .price(BigDecimal.valueOf(10))
            .active(true)
            .build();
    }

    @Test
    @DisplayName("Product create")
    void create() {
        when(gateway.create(any())).thenReturn(dto);
        ProductDTO dtoCreated = service.create(dto);

        assertNotNull(dtoCreated);
        assertEquals(1, dtoCreated.getId());
        assertEquals("123", dtoCreated.getCode());
    }

    @Test
    @DisplayName("Product create, Exception")
    void createException() {
        String existCodeProduct = "123456";
        ProductDTO localDTO = ProductDTO.builder().code(existCodeProduct).build();
        when(gateway.findByCode(any())).thenReturn(localDTO);
        when(messageSource.getMessage(anyString(), any(Object[].class), any(Locale.class)))
            .thenReturn("message");


        Exception exception = assertThrows(DomainException.class, () -> {
            service.create(localDTO);
        });
        assertTrue(exception.getMessage().contains("message"));
        assertEquals(ErrorCode.DOMAIN_RESOURCE_DUPLICATE, ((DomainException) exception).getCode());

        // Verify that gateway.create() is not called if the code already exists
        verify(gateway, never()).create(any(ProductDTO.class));
    }

    @Test
    @DisplayName("Update product")
    void update() {
        when(gateway.update(any())).thenReturn(dto);

        dto.setName("Product Name Updated");
        ProductDTO dtoUpdated = gateway.update(dto);

        assertNotNull(dtoUpdated);
        assertEquals("Product Name Updated", dtoUpdated.getName());
    }

    @Test
    @DisplayName("Update product, exception not exist id")
    void updateExceptionNotExistId() {
        when(messageSource.getMessage(anyString(), any(Object[].class), any(Locale.class)))
            .thenReturn("message");

        Exception exception = assertThrows(DomainException.class, () -> service.update(dto));
        assertTrue(exception.getMessage().contains("message"));
        assertEquals(ErrorCode.DOMAIN_RESOURCE_NOT_FOUND, ((DomainException) exception).getCode());

        // Verify that gateway.update() is not called if the id not exists
        verify(gateway, never()).update(any(ProductDTO.class));
    }
}