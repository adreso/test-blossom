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
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "Orders")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderEntity extends BaseEntity {

	@ManyToOne
	@JoinColumn(name = "idfkuser")
	@NotNull
	private UserEntity user;

	@NotNull
	@Column(name = "dateorder")
	private LocalDateTime dateOrder;

	@NotNull
	@OneToMany(fetch = FetchType.LAZY, orphanRemoval = true, cascade = CascadeType.ALL)
	@JoinColumn(name = "idfkorder", referencedColumnName = "id")
	private List<OrderDetailEntity> detail;

	@JdbcTypeCode(SqlTypes.BOOLEAN)
	private Boolean confirmed;
}
