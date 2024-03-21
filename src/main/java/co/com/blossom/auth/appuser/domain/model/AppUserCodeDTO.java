package co.com.blossom.auth.appuser.domain.model;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AppUserCodeDTO {

	@NotNull
	private String username;

	@NotNull
	private String code;
}
