package co.com.blossom.configs.controller;

import co.com.blossom.configs.utils.StandarResponse;

public abstract class AbstractRestController {

    protected StandarResponse buildSuccessResponseDTO(Object result) {
        return StandarResponse.builder()
                .exitoso(true)
                .dato(result)
                .mensaje("OK")
                .titulo("Successful")
                .codigo("")
                .build();
    }
}
