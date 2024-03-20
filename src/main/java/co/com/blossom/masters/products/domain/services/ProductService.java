package co.com.blossom.masters.products.domain.services;

import co.com.blossom.configs.utils.CustomSlice;
import co.com.blossom.masters.products.domain.model.ProductDTO;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;

public interface ProductService {

	ProductDTO create(ProductDTO productDTO);

	ProductDTO read(Integer id);

	ProductDTO update(ProductDTO productDTO);

	void delete(Integer id);

	CustomSlice<ProductDTO> findByFilter(String name, String category,
										 BigDecimal minorPrice, BigDecimal majorPrice, Pageable pageable);

}
