package co.com.colaboracionelectronica.template.infraestructure.drivenadapter.mysql.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "SegUsuarios")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserEntities {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	Integer id;

	@Column(name = "idusuario")
	String idusuario;
	@Column(name = "idfkempresa")
	Integer idfkempresa;
	@Column(name = "idfkrol")
	Integer idfkrol;
	@Column(name = "tipoid")
	String tipoid;
	@Column(name = "identificacion")
	String identificacion;
	@Column(name = "nombres")
	String nombres;
	@Column(name = "apellidos")
	String apellidos;
	@Column(name = "passwordusuario")
	String passwordusuario;
	@Column(name = "fechaactivacion")
	@Temporal(TemporalType.TIME)
	Date fechaactivacion;
	@Column(name = "fechaexpiracion")
	Date fechaexpiracion;
	@Column(name = "fechaultimocambiopassword")
	Date fechaultimocambiopassword;
	@Column(name = "paginainicio")
	Integer paginainicio;
	@Column(name = "fechaultimoingreso")
	Date fechaultimoingreso;
	@Column(name = "idsesion")
	String idsesion;
	@Column(name = "horaultimoingreso")
	@Temporal(TemporalType.TIME)
	Date horaultimoingreso;
	@Column(name = "estado")
	Integer estado;
	@Column(name = "activo")
	Integer activo;
	@Column(name = "idusuarioalta")
	Integer idusuarioalta;
	@Column(name = "fechaalta")
	Date fechaalta;
	@Column(name = "idusuariomod")
	Integer idusuariomod;
	@Column(name = "fechamod")
	String fechamod;
	@Column(name = "idusuariobaja")
	Integer idusuariobaja;
	@Column(name = "fechabaja")
	Date fechabaja;
	@Column(name = "registraFacturasProveedor")
	Integer registraFacturasProveedor;
	@Column(name = "codigousuario")
	String codigousuario;
	@Column(name = "correoelectronico")
	String correoelectronico;
	@Column(name = "fechaultimotoken")
	Date fechaultimotoken;
	@Column(name = "tokenautenticacion")
	String tokenautenticacion;
	@Column(name = "tiempoAutenticacion")
	String tiempoAutenticacion;
}
