package co.com.blossom.masters.users.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDTO {

	@PositiveOrZero(message = "{common.field.value.positive}")
	private Integer id;

	@NotNull
	private String userName;

	@NotNull
	private String firstName;

	@NotNull
	private String lastName;

	private String password;
}
