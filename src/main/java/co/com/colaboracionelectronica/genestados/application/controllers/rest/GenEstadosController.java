package co.com.colaboracionelectronica.genestados.application.controllers.rest;

import co.com.colaboracionelectronica.external.anotaciones.ExcepcionControl;
import co.com.colaboracionelectronica.external.comunes.ResponseDTO;
import co.com.colaboracionelectronica.genestados.domain.model.GenEstadosDTO;
import co.com.colaboracionelectronica.genestados.domain.model.projections.GenEstadosFullDescripcion;
import co.com.colaboracionelectronica.genestados.domain.model.projections.GenEstadosProjection;
import co.com.colaboracionelectronica.genestados.domain.services.GenEstadosServices;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/genestados")
@AllArgsConstructor
public class GenEstadosController {

    @Autowired
    private GenEstadosServices estadosServices;

    @GetMapping("/v1/{idestado}")
    public ResponseEntity<ResponseDTO> consultarEstado(@PathVariable Integer idestado) {
        GenEstadosDTO result = estadosServices.getGenEstadoByEstado(idestado);

        ResponseDTO response = buildSuccessResponseDTO(result);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/v2/{idestado}")
    public ResponseEntity<ResponseDTO> consultarEstadoProjection(@PathVariable Integer idestado) {
        GenEstadosProjection result = estadosServices.getGenEstadoProjectionByEstado(idestado);

        ResponseDTO response = buildSuccessResponseDTO(result);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/v1")
    @ExcepcionControl
    public ResponseEntity<ResponseDTO> guardarEstado(@Valid @RequestBody GenEstadosDTO genEstados){
        GenEstadosDTO result = estadosServices.setGenEstados(genEstados);

        ResponseDTO response = buildSuccessResponseDTO(result);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
    @GetMapping("/v1/fullestado/{idestado}")
    public ResponseEntity<ResponseDTO> consultarEstadoFullDescripcion(@PathVariable Integer idestado) {
        GenEstadosFullDescripcion result = estadosServices.getEstadoFullDescripcion(idestado);

        ResponseDTO response = buildSuccessResponseDTO(result);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    private ResponseDTO buildSuccessResponseDTO(Object result) {
        return ResponseDTO.builder()
                .exitoso(true)
                .dato(result)
                .mensaje("OK")
                .titulo("Satisfactorio")
                .status(HttpStatus.OK)
                .build();
    }
    
}
