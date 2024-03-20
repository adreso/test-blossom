package co.com.blossom.masters.products.infraestructure.mysql.gateway;

import co.com.blossom.configs.exceptions.DomainException;
import co.com.blossom.configs.utils.CustomSlice;
import co.com.blossom.configs.utils.ErrorCode;
import co.com.blossom.masters.products.domain.gateways.ProductGateway;
import co.com.blossom.masters.products.domain.model.ProductDTO;
import co.com.blossom.masters.products.infraestructure.mysql.ProductRepository;
import co.com.blossom.masters.products.infraestructure.mysql.mapper.ProductMapper;
import co.com.blossom.masters.products.infraestructure.mysql.model.ProductEntity;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.Set;

@Repository
@RequiredArgsConstructor
public class ProductGatewayImpl implements ProductGateway {

    @NotNull
    private final ProductRepository repository;
    @NotNull
    private final ProductMapper productMapper;
    @NotNull
    private final MessageSource messageSource;

    @Override
    public ProductDTO create(ProductDTO productDTO) {
        ProductEntity productEntityToCreate = productMapper.mapModelToEntity(productDTO);
        productEntityToCreate.setId(null);
        productEntityToCreate.setActive(true);
        productEntityToCreate.setDeleted(false);
        ProductEntity productEntityCreated = repository.save(productEntityToCreate);

        return productMapper.mapEntityToModel(productEntityCreated);
    }

    @Override
    public ProductDTO update(ProductDTO productDTO) {
        ProductEntity productEntityToUpdate = productMapper.mapModelToEntity(productDTO);
        ProductEntity productEntityUpdated = repository.save(productEntityToUpdate);
        return productMapper.mapEntityToModel(productEntityUpdated);
    }

    @Override
    public void delete(ProductDTO productDTO) {
        ProductEntity productEntityToUpdate = productMapper.mapModelToEntity(productDTO);
        productEntityToUpdate.setActive(false);
        productEntityToUpdate.setDeleted(true);
        repository.save(productEntityToUpdate);
    }

    @Override
    public ProductDTO findById(Integer id) {
        Optional<ProductEntity> productEntity = repository.findById(id);
        if (productEntity.isPresent() && Boolean.TRUE.equals(productEntity.get().getDeleted())) {
            throw new DomainException(messageSource.getMessage("common.record.deleted",
                    new Object[]{id}, Locale.getDefault()), ErrorCode.DOMAIN_RESOURCE_DELETED);
        }
        return productEntity.map(productMapper::mapEntityToModel).orElse(null);
    }

    @Override
    public ProductDTO findByCode(String code) {
        return repository.findByCode(code)
                .map(productMapper::mapEntityToModel)
                .orElse(null);
    }

    @Override
    public CustomSlice<ProductDTO> findByFilter(String name, String category,
                                                BigDecimal priceMinor, BigDecimal priceMajor,
                                                Pageable pageable) {
        name = name == null ? "" : name;
        category = category == null ? "" : category;
        priceMinor = priceMinor == null ? BigDecimal.ZERO : priceMinor;
        priceMajor = priceMajor == null ? BigDecimal.valueOf(999999999) : priceMajor;
        Page<ProductEntity> page = repository.findByFilter(name, category, priceMinor, priceMajor, pageable);
        return CustomSlice.<ProductDTO>builder().
            elements(page.map(productMapper::mapEntityToModel).stream().toList())
            .page(page)
            .build();
    }

    @Override
    public boolean existAllProductos(Set<Integer> ids) {
        List<ProductEntity> list = repository.findAllByIdIn(ids);
        return list.size() == ids.size();
    }

}
