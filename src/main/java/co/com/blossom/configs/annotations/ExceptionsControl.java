package co.com.blossom.configs.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Example annotation to identify methods that are desired to be observed if they trigger an exception
 * Required by the ExceptionsAspect class
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ExceptionsControl {

}
