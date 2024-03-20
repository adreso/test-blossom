package co.com.blossom.configs.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Anotación de ejemplo para identificar métodos que se deseen observar si detonan excepción
 * Lo requiere la clase ExceptionsAspect
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ExceptionsControl {

}
