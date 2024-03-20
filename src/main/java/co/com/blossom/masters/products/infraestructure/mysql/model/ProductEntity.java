package co.com.blossom.masters.products.infraestructure.mysql.model;

import co.com.blossom.configs.enumerators.ProductBrandENUM;
import co.com.blossom.configs.enumerators.ProductCategoryENUM;
import co.com.blossom.configs.enumerators.RoleENUM;
import co.com.blossom.configs.infraestructure.entities.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;

@Entity
@Table(name = "Products")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductEntity extends BaseEntity {

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

	@NotNull
	@JdbcTypeCode(SqlTypes.BOOLEAN)
	private Boolean active;

	@JdbcTypeCode(SqlTypes.BOOLEAN)
	private Boolean deleted;
}
