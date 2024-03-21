package co.com.blossom.configs.security.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Builder
@Getter
@Setter
public class TokenDetail {
   private Integer idUsuario;
   private Integer idEmpresa;
   private Integer idAplicacion;
   private Integer idEmpresaGrupo;
   private List<Integer> empresasVinculadasAUsuario;
   private String idSesion;
   private String nombreUsuario;
   private String nombreEmpresa;
   private List<String> roles;
   private List<Integer> rolesIds;
   private boolean multisesion;
}
