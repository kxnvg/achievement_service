package faang.school.achievement.exception;

import java.text.MessageFormat;

public class EntityNotFoundException extends RuntimeException {

    public EntityNotFoundException(String entityName, String identifier) {
        super(MessageFormat.format("{0} with identifier {1} not found.", entityName, identifier));
    }

    public EntityNotFoundException(String entityName, String identifier, Throwable cause) {
        super(MessageFormat.format("{0} with identifier {1} not found.", entityName, identifier), cause);
    }

    @Override
    public String getMessage() {
        if (getCause() != null) {
            return MessageFormat.format("{0} Reason: {1}", super.getMessage(), getCause().getMessage());
        }
        return super.getMessage();
    }
}
