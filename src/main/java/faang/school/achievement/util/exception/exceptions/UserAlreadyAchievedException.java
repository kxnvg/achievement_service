package faang.school.achievement.util.exception.exceptions;

public class UserAlreadyAchievedException extends RuntimeException {
    public UserAlreadyAchievedException(String achievementTitle) {
        super("User has already achieved: " + achievementTitle);
    }
}
