package co.com.colaboracionelectronica.genaplicacion.application.controllers.rest;

import co.com.colaboracionelectronica.external.comunes.ResponseDTO;
import co.com.colaboracionelectronica.genaplicacion.domain.model.Aplicacion;
import co.com.colaboracionelectronica.genaplicacion.domain.services.AplicacionService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/aplicacion", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class AplicacionController {

    private final AplicacionService aplicacionService;

    @GetMapping(value = "/ping")
    public ResponseEntity<Object> ping() {
        return new ResponseEntity<>("pong2", HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDTO> getAplicacionById(@PathVariable Integer id) {
        Aplicacion aplicacion = aplicacionService.getAplicacionById(id);
        ResponseDTO response = buildSuccessResponseDTO(aplicacion);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ResponseDTO> postAplicacion(@RequestBody Aplicacion requestAplicacion) {
        ResponseDTO response;
        Aplicacion aplicacionCreate = aplicacionService.createAplicacion(requestAplicacion);

        if (aplicacionCreate.getIdAplicacion() != null) {
            response = buildSuccessResponseDTO(aplicacionCreate);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        }
        response = buildFailResponseDTO(null,"No se pudo crear",HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
    @PutMapping
    public ResponseEntity<ResponseDTO> putAplicacionById(@RequestBody Aplicacion requestAplicacion) {
        Aplicacion aplicacion = aplicacionService.editAplicacionById(requestAplicacion);
        ResponseDTO response = buildSuccessResponseDTO(aplicacion);
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

    private ResponseDTO buildFailResponseDTO(Object result, String titulo, HttpStatus status) {
        return ResponseDTO.builder()
                .exitoso(false)
                .dato(result)
                .mensaje("ERROR")
                .titulo(titulo)
                .status(status)
                .build();
    }
}
