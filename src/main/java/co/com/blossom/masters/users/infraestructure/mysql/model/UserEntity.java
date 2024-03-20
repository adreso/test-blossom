package co.com.blossom.masters.users.infraestructure.mysql.model;

import co.com.blossom.configs.enumerators.RoleENUM;
import co.com.blossom.configs.infraestructure.entities.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import org.hibernate.type.SqlTypes;

@Entity
@Table(name = "Users")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserEntity extends BaseEntity {

	@NotNull
	@Column(name="username")
	private String userName;

	@NotNull
	@Column(name="firstname")
	private String firstName;

	@NotNull
	@Column(name="lastname")
	private String lastName;

	@NotNull
	private String role;

	@NotNull
	@JdbcTypeCode(SqlTypes.BOOLEAN)
	private Boolean active;

	@JdbcTypeCode(SqlTypes.BOOLEAN)
	private Boolean deleted;
}
