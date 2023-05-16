package faang.school.achievement.exception;

public class EntityNotFoundException extends BusinessException {
    public EntityNotFoundException(String code, String message) {
        super(code, message);
    }
}
