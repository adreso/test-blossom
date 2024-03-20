package co.com.blossom.configs.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BaseException extends RuntimeException{
    private String code;
    private String message;

    private String exceptionMsg;

    public String getLogMessage() {
        StringBuilder logMessage = new StringBuilder(code);
        logMessage.append(": ").append(message);

        if(Objects.nonNull(exceptionMsg)) {
            logMessage.append("\n");
            logMessage.append(exceptionMsg);
        }

        return logMessage.toString();
    }
}
