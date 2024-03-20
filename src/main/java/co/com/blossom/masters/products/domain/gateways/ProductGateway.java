package co.com.blossom.masters.products.domain.gateways;

import co.com.blossom.configs.utils.CustomSlice;
import co.com.blossom.masters.products.domain.model.ProductDTO;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

public interface ProductGateway {

	ProductDTO create(ProductDTO productDTO);

	ProductDTO update(ProductDTO productDTO);

	void delete(ProductDTO id);

	ProductDTO findById(Integer id);
	ProductDTO findByCode(String code);

	CustomSlice<ProductDTO> findByFilter(String name, String category,
										 BigDecimal priceMinor, BigDecimal priceMajor,
										 Pageable pageable);

	boolean existAllProductos(Set<Integer> ids);
}
