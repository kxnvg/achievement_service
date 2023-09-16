package faang.school.achievement.exception;

import java.text.MessageFormat;

public class AchievementNotFoundException extends RuntimeException {

    private final String errorMessage;

    public AchievementNotFoundException(String achievementName) {
        super("Achievement not found");
        this.errorMessage = MessageFormat.format("Achievement with name {0} not found.", achievementName);
    }

    @Override
    public String getMessage() {
        return errorMessage;
    }
}
