package faang.school.achievement.exception;

import java.text.MessageFormat;

public class AchievementNotInCacheException extends RuntimeException {
    public AchievementNotInCacheException(String achievementName) {
        super(MessageFormat.format("Achievement with name {0} not found in cache.", achievementName));
    }
}
