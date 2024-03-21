package co.com.blossom.inventorys.orders.infraestructure.mysql.model;

import co.com.blossom.configs.infraestructure.entities.BaseEntity;
import co.com.blossom.masters.products.infraestructure.mysql.model.ProductEntity;
import co.com.blossom.masters.users.infraestructure.mysql.model.UserEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "OrdersDetail")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderDetailEntity extends BaseEntity {

	@ManyToOne
	@JoinColumn(name = "idfkproduct")
	@NotNull
	private ProductEntity product;

	@NotNull
	private BigDecimal quantity;

	@NotNull
	private BigDecimal price;
}
