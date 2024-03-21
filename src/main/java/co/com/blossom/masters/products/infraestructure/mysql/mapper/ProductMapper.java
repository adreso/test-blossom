package co.com.blossom.masters.products.infraestructure.mysql.mapper;

import co.com.blossom.masters.products.domain.model.ProductDTO;
import co.com.blossom.masters.products.infraestructure.mysql.model.ProductEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProductMapper {

	ProductDTO mapEntityToModel(ProductEntity productEntity);
	ProductEntity mapModelToEntity(ProductDTO productDTO);

}
