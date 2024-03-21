package co.com.blossom.masters.products.infraestructure.mysql;

import co.com.blossom.configs.enumerators.ProductBrandENUM;
import co.com.blossom.configs.enumerators.ProductCategoryENUM;
import co.com.blossom.configs.enumerators.RoleENUM;
import co.com.blossom.configs.exceptions.DomainException;
import co.com.blossom.configs.utils.CustomSlice;
import co.com.blossom.configs.utils.ErrorCode;
import co.com.blossom.masters.products.domain.model.ProductDTO;
import co.com.blossom.masters.products.infraestructure.mysql.ProductRepository;
import co.com.blossom.masters.products.infraestructure.mysql.gateway.ProductGatewayImpl;
import co.com.blossom.masters.products.infraestructure.mysql.mapper.ProductMapper;
import co.com.blossom.masters.products.infraestructure.mysql.model.ProductEntity;
import co.com.blossom.masters.users.domain.model.UserDTO;
import co.com.blossom.masters.users.infraestructure.mysql.model.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductGatewayImplTest {
    @Mock
    private ProductRepository repository;
    @Spy
    private ProductMapper mapper = Mappers.getMapper(ProductMapper.class);
    @InjectMocks
    private ProductGatewayImpl gateway;

    private ProductEntity entity;
    private ProductDTO dto;
    @Mock
    private MessageSource messageSource;

    @BeforeEach
    void setUp() {
        entity = new ProductEntity();
        entity.setId(1);
        entity.setCode("123");
        entity.setName("Product");
        entity.setCategory(ProductCategoryENUM.CATEGORY_A.name());
        entity.setBrand(ProductBrandENUM.BRAND_A.name());
        entity.setPrice(new BigDecimal(10.5));
        entity.setActive(true);
        entity.setDeleted(false);

        dto = ProductDTO.builder()
            .id(1)
            .code("123")
            .name("Product")
            .category(ProductCategoryENUM.CATEGORY_A.name())
            .brand(ProductBrandENUM.BRAND_A.name())
            .price(new BigDecimal(10.5))
            .build();
    }

    @Test
    @DisplayName("Product create")
    void create() {
        dto.setId(null);

        when(mapper.mapEntityToModel(entity)).thenReturn(dto);
        when(repository.save(any())).thenReturn(entity);
        ProductDTO productCreate = gateway.create(dto);

        assertNotNull(productCreate);
        assertEquals("Product", productCreate.getName());
    }

    @Test
    @DisplayName("Product update")
    void update() {
        when(mapper.mapEntityToModel(entity)).thenReturn(dto);
        when(mapper.mapModelToEntity(dto)).thenReturn(entity);
        when(repository.save(any())).thenReturn(entity);
        ProductDTO productUpdated = gateway.update(dto);

        assertNotNull(productUpdated);
        assertEquals("Product", productUpdated.getName());
    }

    @Test
    @DisplayName("Product delete")
    void delete() {
        when(mapper.mapModelToEntity(dto)).thenReturn(entity);
        when(repository.save(any())).thenReturn(entity);
        gateway.delete(dto);

        verify(repository, times(1)).save(any(ProductEntity.class));
    }

    @Test
    @DisplayName("Product find by id")
    void findById() {
        when(mapper.mapEntityToModel(entity)).thenReturn(dto);
        when(repository.findById(any())).thenReturn(Optional.of(entity));
        gateway.findById(1);
    }


    @Test
    @DisplayName("Product find by id, exception mark deleted")
    void findByIdExceptionDeleted() {
        ProductEntity productEntity = new ProductEntity();
        productEntity.setDeleted(true);
        when(repository.findById(any())).thenReturn(Optional.of(productEntity));

        when(messageSource.getMessage(anyString(), any(Object[].class), any(Locale.class)))
            .thenReturn("message");

        Exception exception = assertThrows(DomainException.class, () -> gateway.findById(1));
        assertTrue(exception.getMessage().contains("message"));
        assertEquals(ErrorCode.DOMAIN_RESOURCE_DELETED, ((DomainException) exception).getCode());
    }

    @Test
    @DisplayName("Product find by code")
    void findByCode() {
        when(mapper.mapEntityToModel(entity)).thenReturn(dto);
        when(repository.findByCode(any(String.class))).thenReturn(Optional.of(entity));
        gateway.findByCode("123");
    }

    @Test
    @DisplayName("Product find by filter")
    void findByFilter() {
        String name = "productName";
        String category = "productCategory";
        BigDecimal priceMinor = BigDecimal.TEN;
        BigDecimal priceMajor = BigDecimal.valueOf(100);
        Pageable pageable = Pageable.ofSize(10).withPage(0);

        List<ProductEntity> productEntities = new ArrayList<>();
        productEntities.add(new ProductEntity("CODE1", "Product 1", ProductCategoryENUM.CATEGORY_A.name(),
            ProductBrandENUM.BRAND_A.name(), BigDecimal.valueOf(10), true, false));
        productEntities.add(new ProductEntity("CODE2", "Product 2", ProductCategoryENUM.CATEGORY_A.name(),
            ProductBrandENUM.BRAND_A.name(), BigDecimal.valueOf(10), true, false));

        Page<ProductEntity> page = new PageImpl<>(productEntities, pageable, productEntities.size());

        when(repository.findByFilter(name, category, priceMinor, priceMajor, pageable)).thenReturn(page);

        List<ProductDTO> productDTOs = new ArrayList<>();
        productEntities.forEach(entity -> productDTOs.add(ProductDTO.builder().id(entity.getId())
            .name(entity.getName())
            .category(entity.getCategory())
            .brand(entity.getBrand())
            .price(entity.getPrice())
            .build()));
        when(mapper.mapEntityToModel(any())).thenReturn(productDTOs.get(0), productDTOs.get(1));

        // Act
        CustomSlice<ProductDTO> result = gateway.findByFilter(name, category, priceMinor, priceMajor, pageable);

        // Assert
        assertEquals(productEntities.size(), result.getElements().size());
        assertEquals(0, result.getPage());
        assertEquals(2, result.getTotalPage());
        assertEquals(productEntities.size(), result.getTotalElements());
    }



    @Test
    @DisplayName("Product validation, exists all id products")
    void existAllProductos() {
        Set<Integer> mockIds = new HashSet<>();
        mockIds.add(1);
        mockIds.add(2);
        mockIds.add(3);
        List<ProductEntity> listEntities=new ArrayList<>();
        listEntities.add(new ProductEntity());
        listEntities.add(new ProductEntity());
        listEntities.add(new ProductEntity());
        when(repository.findAllByIdIn(mockIds)).thenReturn(listEntities);

        boolean output = gateway.existAllProductos(mockIds);
        assertNotNull(output);
        assertEquals(true, output);
    }


}