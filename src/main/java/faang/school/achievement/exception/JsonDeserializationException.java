package faang.school.achievement.exception;

import java.text.MessageFormat;

public class JsonDeserializationException extends RuntimeException {

    public JsonDeserializationException(String message, Throwable cause) {
        super(message, cause);
    }

    public JsonDeserializationException(Throwable cause) {
        this("Failed to deserialize JSON message.", cause);
    }

    @Override
    public String getMessage() {
        return MessageFormat.format("{0} Reason: {1}", super.getMessage(), getCause().getMessage());
    }
}
