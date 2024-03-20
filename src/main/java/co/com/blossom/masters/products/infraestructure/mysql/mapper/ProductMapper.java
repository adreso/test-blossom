package co.com.blossom.masters.products.infraestructure.mysql.mapper;

import co.com.blossom.configs.enumerators.ProductCategoryENUM;
import co.com.blossom.masters.products.domain.model.ProductDTO;
import co.com.blossom.masters.products.infraestructure.mysql.model.ProductEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.mapstruct.Qualifier;
import org.mapstruct.ReportingPolicy;

import java.util.Objects;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProductMapper {

	ProductDTO mapEntityToModel(ProductEntity productEntity);
	ProductEntity mapModelToEntity(ProductDTO productDTO);

//	@Named("categoryName")
//	default String getCategoryName(String value) {
//		if (Objects.isNull(value)) return null;
//		return ProductCategoryENUM.getByValue(value).name();
//	}

}
