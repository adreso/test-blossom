package co.com.blossom.inventory.application.rest;

import co.com.blossom.configs.enumerators.ProductBrandENUM;
import co.com.blossom.configs.enumerators.ProductCategoryENUM;
import co.com.blossom.masters.products.application.rest.ProductController;
import co.com.blossom.masters.products.domain.model.ProductDTO;
import co.com.blossom.masters.products.domain.services.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
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
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class ProductControllerTest {
    @Mock
    private ProductService service;
    @InjectMocks
    private ProductController controller;
    private MockMvc mockMvc;
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

        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setCustomArgumentResolvers(
                        new PageableHandlerMethodArgumentResolver())
                .build();
    }

    @Test
    @DisplayName("Product creation")
    void createRecord() throws Exception {
        when(service.create(any())).thenReturn(dto);

        mockMvc.perform(post("/v1/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(dto)))
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("Product update")
    void updateRecord() throws Exception {
        when(service.update(any())).thenReturn(dto);

        mockMvc.perform(put("/v1/products/{1}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(dto)))
            .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Product delete")
    void deleteRecord() throws Exception {
        mockMvc.perform(delete("/v1/products/{1}", 1))
            .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Product find by id")
    void findRecord() throws Exception {
        when(service.read(any())).thenReturn(dto);

        mockMvc.perform(get("/v1/products/{1}/", 1))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath(("$.dato.name"), is("Product Name")));
    }

    @Test
    @DisplayName("Product find by filter")
    void findRecordList() throws Exception {
        mockMvc.perform(get("/v1/products/list")
                .param("page", "1")
                .param("size", "4"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        ArgumentCaptor<Pageable> sortCaptor = ArgumentCaptor.forClass(Pageable.class);
        verify(service).findByFilter(any(), any(), any(), any(), sortCaptor.capture());
        Pageable pageable = sortCaptor.getValue();
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