package co.com.blossom.masters.products.domain.model;

import co.com.blossom.configs.enumerators.ProductBrandENUM;
import co.com.blossom.configs.enumerators.ProductCategoryENUM;
import jakarta.persistence.PrePersist;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.bind.DefaultValue;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductDTO {

	@PositiveOrZero(message = "{common.field.value.positive}")
	private Integer id;

	@NotNull
	private String code;

	@NotNull
	private String name;

	@NotNull
	private String category;

	@NotNull
	private String brand;

	@NotNull
	private BigDecimal price;

	private Boolean active = Boolean.TRUE;
}
