package faang.school.achievement.service;

import faang.school.achievement.cache.AchievementCache;
import faang.school.achievement.model.Achievement;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AchievementService {
    private final AchievementCache achievementCache;

    public Achievement getAchievement(String type) {
        return achievementCache.getAchievement(type);
    }


    public void hasAchievement() {

    }

    public void createProgressIfNecessary() {

    }

    public void getProgress() {

    }

    public Achievement giveAchievement(String title) {
        return achievementCache.getAchievement(title);
    }
}
