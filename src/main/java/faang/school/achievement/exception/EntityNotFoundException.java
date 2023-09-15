package faang.school.achievement.exception;


import java.text.MessageFormat;

public class EntityNotFoundException extends RuntimeException {
    private final String errorMessage;

    public EntityNotFoundException(String entityName, String identifier) {
        super("Entity not found");
        this.errorMessage = MessageFormat.format("{0} with identifier {1} not found.", entityName, identifier);
    }

    public EntityNotFoundException(String entityName, String identifier, Throwable cause) {
        super("Entity not found", cause);
        this.errorMessage = MessageFormat.format("{0} with identifier {1} not found.", entityName, identifier);
    }

    public EntityNotFoundException(String entityName, Long identifier) {
        super("Entity not found");
        this.errorMessage = MessageFormat.format("{0} with identifier {1} not found.", entityName, identifier);
    }

    public EntityNotFoundException(String entityName, Long identifier, Throwable cause) {
        super("Entity not found", cause);
        this.errorMessage = MessageFormat.format("{0} with identifier {1} not found.", entityName, identifier);
    }

    @Override
    public String getMessage() {
        if (getCause() != null) {
            return MessageFormat.format("{0} Reason: {1}", errorMessage, getCause().getMessage());
        }
        return errorMessage;
    }
}