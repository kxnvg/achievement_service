package faang.school.achievement.achievement_test;

import faang.school.achievement.model.Achievement;
import org.springframework.stereotype.Component;

@Component
public class AchievementCache {

    public Achievement get(String title) {
       return new Achievement();
    }
}
