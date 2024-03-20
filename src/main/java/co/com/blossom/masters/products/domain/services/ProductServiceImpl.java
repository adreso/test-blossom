package co.com.blossom.masters.products.domain.services;

import co.com.blossom.configs.enumerators.ProductBrandENUM;
import co.com.blossom.configs.enumerators.ProductCategoryENUM;
import co.com.blossom.configs.exceptions.DomainException;
import co.com.blossom.configs.utils.CustomSlice;
import co.com.blossom.configs.utils.ErrorCode;
import co.com.blossom.masters.products.domain.gateways.ProductGateway;
import co.com.blossom.masters.products.domain.model.ProductDTO;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Locale;
import java.util.Objects;

@Service
@AllArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {

	private final ProductGateway gateway;
	private final MessageSource messageSource;

	@Override
	public ProductDTO create(ProductDTO productDTO) {
		log.info("Registering product {}", productDTO);

		if(Objects.nonNull(gateway.findByCode(productDTO.getCode()))) {
			throw new DomainException(messageSource.getMessage("common.record.duplicated",
				new Object[] {productDTO.getCode()}, Locale.getDefault()), ErrorCode.DOMAIN_RESOURCE_DUPLICATE);
		}

		validateDataProduct(productDTO);

		return gateway.create(productDTO);
	}

	@Override
	public ProductDTO read(Integer id) {
		return gateway.findById(id);
	}

	@Override
	public ProductDTO update(ProductDTO productDTO) {
		log.info("Updating product {}", productDTO);
		if (Objects.isNull(gateway.findById(productDTO.getId()))) {
			throw new DomainException(messageSource.getMessage("common.record.not.found",
				new Object[] {productDTO.getId()}, Locale.getDefault()), ErrorCode.DOMAIN_RESOURCE_NOT_FOUND);
		}

		validateDataProduct(productDTO);

		return gateway.update(productDTO);
	}

	@Override
	public void delete(Integer id) {
		log.info("Deleting product {}", id);
		ProductDTO productDTO = gateway.findById(id);
		if (Objects.isNull(productDTO)) {
			throw new DomainException(messageSource.getMessage("common.record.not.found",
				new Object[] {id}, Locale.getDefault()), ErrorCode.DOMAIN_RESOURCE_NOT_FOUND);
		}

		gateway.delete(productDTO);
	}

	@Override
	public CustomSlice<ProductDTO> findByFilter(String name, String category,
												BigDecimal minorPrice, BigDecimal majorPrice, Pageable pageable) {
		return gateway.findByFilter(name, category, minorPrice, majorPrice, pageable);
	}

	private boolean validateDataProduct(ProductDTO productDTO) {

		if (Objects.isNull(ProductCategoryENUM.getByValue(productDTO.getCategory()))) {
			throw new DomainException(messageSource.getMessage("common.record.not.found",
				new Object[] {productDTO.getCategory()}, Locale.getDefault()), ErrorCode.DOMAIN_RESOURCE_NOT_FOUND);
		}

		if (Objects.isNull(ProductBrandENUM.getByValue(productDTO.getBrand()))) {
			throw new DomainException(messageSource.getMessage("common.record.not.found",
				new Object[] {productDTO.getBrand()}, Locale.getDefault()), ErrorCode.DOMAIN_RESOURCE_NOT_FOUND);
		}

		if (Objects.isNull(productDTO.getPrice()) || productDTO.getPrice().doubleValue() < 0) {
			throw new DomainException(messageSource.getMessage("product.price.validation.negative",
				new Object[] {productDTO.getCode()}, Locale.getDefault()), ErrorCode.DOMAIN_VALUE_FIELD_INVALID);
		}

		return true;
	}
}
