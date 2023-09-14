package faang.school.achievement.exception;

import java.text.MessageFormat;

public class JsonDeserializationException extends RuntimeException {
    private final String errorMessage;

    public JsonDeserializationException(String message, Throwable cause) {
        super(message, cause);
        this.errorMessage = message;
    }

    public JsonDeserializationException(Throwable cause) {
        this("Failed to deserialize JSON message.", cause);
    }

    @Override
    public String getMessage() {
        return MessageFormat.format("{0} Reason: {1}", errorMessage, getCause().getMessage());
    }
}
