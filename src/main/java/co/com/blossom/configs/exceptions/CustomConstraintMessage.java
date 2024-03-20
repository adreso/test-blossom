package co.com.blossom.configs.exceptions;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;

import java.util.Locale;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@AllArgsConstructor
public class CustomConstraintMessage implements ConstraintFacade {

    private final MessageSource messageSource;

    @Override
    public String getEffectiveMessage(String messageException) {
        String message = null;
        String constraintName = getNombreConstraint(messageException);

        if(Objects.nonNull(constraintName)) {
            String parameterCode = "comun.constraint.".concat(constraintName);
            message = messageSource.getMessage(parameterCode, null, "", Locale.getDefault());

            if(message.isEmpty()) {
                log.warn("No controlled Constraint: " + constraintName);
            }
        }

        return message;
    }

    private String getNombreConstraint(String messageException) {
        Matcher matcher = Pattern.compile("\\[(.*?)\\]")
            .matcher(messageException);
        String ultimoTextoEntreCorchetes = null;

        while (matcher.find()) {
            ultimoTextoEntreCorchetes = matcher.group(1);
        }

        return ultimoTextoEntreCorchetes;
    }
}
