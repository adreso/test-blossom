package co.com.colaboracionelectronica.template.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class User{

	Integer id;
	String idusuario;
	Integer idfkempresa;
	String tipoid;
	String identificacion;
	String nombres;
	String apellidos;
	String passwordusuario;
}
