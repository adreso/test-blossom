package co.com.blossom.masters.products.domain.services;

import co.com.blossom.configs.enumerators.ProductBrandENUM;
import co.com.blossom.configs.enumerators.ProductCategoryENUM;
import co.com.blossom.configs.exceptions.DomainException;
import co.com.blossom.configs.utils.CustomSlice;
import co.com.blossom.configs.utils.ErrorCode;
import co.com.blossom.masters.products.domain.gateways.ProductGateway;
import co.com.blossom.masters.products.domain.model.ProductDTO;
import co.com.blossom.masters.products.domain.services.ProductServiceImpl;
import co.com.blossom.masters.products.infraestructure.mysql.model.ProductEntity;
import co.com.blossom.masters.users.domain.model.UserDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
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
    @DisplayName("Product create, exception exist code product")
    void createExceptionDuplicateCode() {
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
    @DisplayName("Product update, exception category not found")
    void createExceptionCategoyNotFund() {
        when(messageSource.getMessage(anyString(), any(Object[].class), any(Locale.class)))
            .thenReturn("message");

        ProductDTO localDTO =ProductDTO.builder()
            .id(1)
            .code("456")
            .name("Product Two Name")
            .category("CATEGORY_NOT_VALID")
            .brand(ProductBrandENUM.BRAND_A.name())
            .price(BigDecimal.valueOf(10))
            .active(true)
            .build();

        Exception exception = assertThrows(DomainException.class, () -> service.create(localDTO));
        assertTrue(exception.getMessage().contains("message"));
        assertEquals(ErrorCode.DOMAIN_RESOURCE_NOT_FOUND, ((DomainException) exception).getCode());

        // Verify that gateway.update() is not called if the id not exists
        verify(gateway, never()).update(any(ProductDTO.class));
    }

    @Test
    @DisplayName("Product update, exception brand not found")
    void createExceptionBrandNotFund() {
        when(messageSource.getMessage(anyString(), any(Object[].class), any(Locale.class)))
            .thenReturn("message");

        ProductDTO localDTO =ProductDTO.builder()
            .id(1)
            .code("456")
            .name("Product Two Name")
            .category(ProductCategoryENUM.CATEGORY_A.name())
            .brand("BRAND_NOT_VALID")
            .price(BigDecimal.valueOf(10))
            .active(true)
            .build();

        Exception exception = assertThrows(DomainException.class, () -> service.create(localDTO));
        assertTrue(exception.getMessage().contains("message"));
        assertEquals(ErrorCode.DOMAIN_RESOURCE_NOT_FOUND, ((DomainException) exception).getCode());

        // Verify that gateway.update() is not called if the id not exists
        verify(gateway, never()).update(any(ProductDTO.class));
    }

    @Test
    @DisplayName("Product update, exception price invalid")
    void createExceptionPriceInvalid() {
        when(messageSource.getMessage(anyString(), any(Object[].class), any(Locale.class)))
            .thenReturn("message");

        ProductDTO localDTO =ProductDTO.builder()
            .id(1)
            .code("456")
            .name("Product Two Name")
            .category(ProductCategoryENUM.CATEGORY_A.name())
            .brand(ProductBrandENUM.BRAND_A.name())
            .price(BigDecimal.valueOf(-1))
            .active(true)
            .build();

        Exception exception = assertThrows(DomainException.class, () -> service.create(localDTO));
        assertTrue(exception.getMessage().contains("message"));
        assertEquals(ErrorCode.DOMAIN_VALUE_FIELD_INVALID, ((DomainException) exception).getCode());

        // Verify that gateway.update() is not called if the id not exists
        verify(gateway, never()).update(any(ProductDTO.class));
    }

    @Test
    @DisplayName("Product update")
    void update() {
        when(gateway.findById(any())).thenReturn(dto);
        when(gateway.update(any())).thenReturn(dto);

        dto.setName("Product Name Updated");
        ProductDTO dtoUpdated = service.update(dto);

        assertNotNull(dtoUpdated);
        assertEquals("Product Name Updated", dtoUpdated.getName());
    }

    @Test
    @DisplayName("Product update, exception not exist id")
    void updateExceptionNotExistId() {
        when(messageSource.getMessage(anyString(), any(Object[].class), any(Locale.class)))
            .thenReturn("message");

        Exception exception = assertThrows(DomainException.class, () -> service.update(dto));
        assertTrue(exception.getMessage().contains("message"));
        assertEquals(ErrorCode.DOMAIN_RESOURCE_NOT_FOUND, ((DomainException) exception).getCode());

        // Verify that gateway.update() is not called if the id not exists
        verify(gateway, never()).update(any(ProductDTO.class));
    }

    @Test
    @DisplayName("Product update, exception category not found")
    void updateExceptionCategoyNotFund() {
        when(gateway.findById(any())).thenReturn(dto);
        when(messageSource.getMessage(anyString(), any(Object[].class), any(Locale.class)))
            .thenReturn("message");

        ProductDTO localDTO =ProductDTO.builder()
            .id(1)
            .code("456")
            .name("Product Two Name")
            .category("CATEGORY_NOT_VALID")
            .brand(ProductBrandENUM.BRAND_A.name())
            .price(BigDecimal.valueOf(10))
            .active(true)
            .build();

        Exception exception = assertThrows(DomainException.class, () -> service.update(localDTO));
        assertTrue(exception.getMessage().contains("message"));
        assertEquals(ErrorCode.DOMAIN_RESOURCE_NOT_FOUND, ((DomainException) exception).getCode());

        // Verify that gateway.update() is not called if the id not exists
        verify(gateway, never()).update(any(ProductDTO.class));
    }

    @Test
    @DisplayName("Product update, exception brand not found")
    void updateExceptionBrandNotFund() {
        when(gateway.findById(any())).thenReturn(dto);
        when(messageSource.getMessage(anyString(), any(Object[].class), any(Locale.class)))
            .thenReturn("message");

        ProductDTO localDTO =ProductDTO.builder()
            .id(1)
            .code("456")
            .name("Product Two Name")
            .category(ProductCategoryENUM.CATEGORY_A.name())
            .brand("BRAND_NOT_VALID")
            .price(BigDecimal.valueOf(10))
            .active(true)
            .build();

        Exception exception = assertThrows(DomainException.class, () -> service.update(localDTO));
        assertTrue(exception.getMessage().contains("message"));
        assertEquals(ErrorCode.DOMAIN_RESOURCE_NOT_FOUND, ((DomainException) exception).getCode());

        // Verify that gateway.update() is not called if the id not exists
        verify(gateway, never()).update(any(ProductDTO.class));
    }

    @Test
    @DisplayName("Product update, exception price invalid")
    void updateaExceptionPriceInvalid() {
        when(gateway.findById(any())).thenReturn(dto);
        when(messageSource.getMessage(anyString(), any(Object[].class), any(Locale.class)))
            .thenReturn("message");

        ProductDTO localDTO =ProductDTO.builder()
            .id(1)
            .code("456")
            .name("Product Two Name")
            .category(ProductCategoryENUM.CATEGORY_A.name())
            .brand(ProductBrandENUM.BRAND_A.name())
            .price(BigDecimal.valueOf(-1))
            .active(true)
            .build();

        Exception exception = assertThrows(DomainException.class, () -> service.update(localDTO));
        assertTrue(exception.getMessage().contains("message"));
        assertEquals(ErrorCode.DOMAIN_VALUE_FIELD_INVALID, ((DomainException) exception).getCode());

        // Verify that gateway.update() is not called if the id not exists
        verify(gateway, never()).update(any(ProductDTO.class));
    }

    @Test
    @DisplayName("Product delete")
    void deleteRecord() {
        when(gateway.findById(anyInt())).thenReturn(dto);
        service.delete(1);
        verify(gateway, times(1)).delete(dto);
    }


    @Test
    @DisplayName("Product delete, exception not exist id")
    void deleteExceptionNotExistId() {
        when(messageSource.getMessage(anyString(), any(Object[].class), any(Locale.class)))
            .thenReturn("message");

        Exception exception = assertThrows(DomainException.class, () -> service.delete(1));
        assertTrue(exception.getMessage().contains("message"));
        assertEquals(ErrorCode.DOMAIN_RESOURCE_NOT_FOUND, ((DomainException) exception).getCode());

        // Verify that gateway.delete() is not called if the id not exists
        verify(gateway, never()).delete(any(ProductDTO.class));
    }

    @Test
    @DisplayName("Product find by id")
    void findRecordById() {
        when(gateway.findById(any())).thenReturn(dto);

        ProductDTO dtoFinded = service.read(1);
        assertNotNull(dtoFinded);
        assertEquals(1, dtoFinded.getId());
    }

    @Test
    @DisplayName("Product find by filter")
    void findRecordsByFilter() {
        // Arrange
        String name = "productName";
        String category = "productCategory";
        BigDecimal minorPrice = BigDecimal.ZERO;
        BigDecimal majorPrice = BigDecimal.TEN;
        Pageable pageable = Pageable.ofSize(10).withPage(0);

        // Mock the result of the gateway
        List<ProductDTO> products = new ArrayList<>();
        products.add(ProductDTO.builder().id(1).name("Product 1").category("Category 1").price(BigDecimal.valueOf(10)).build());
        products.add(ProductDTO.builder().id(2).name("Product 2").category("Category 1").price(BigDecimal.valueOf(15)).build());

        CustomSlice<ProductDTO> customSlice = CustomSlice.<ProductDTO>builder()
            .elements(products)
            .page(new PageImpl(products))
            .build();

        when(gateway.findByFilter(name, category, minorPrice, majorPrice, pageable)).thenReturn(customSlice);

        // Act
        CustomSlice<ProductDTO> result = service.findByFilter(name, category, minorPrice, majorPrice, pageable);

        // Assert
        assertEquals(products, result.getElements());
        assertEquals(products.size(), result.getTotalElements());
        assertEquals(0, result.getPage());
        assertEquals(2, result.getTotalPage());
    }
}