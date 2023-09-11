package faang.school.achievement.handler;

import faang.school.achievement.cache.AchievementCache;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.model.AchievementProgress;
import faang.school.achievement.model.UserAchievement;
import faang.school.achievement.service.AchievementService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;

@RequiredArgsConstructor
public abstract class AbstractAchievementHandler<T> implements EventHandler<T> {
    protected final AchievementService achievementService;
    protected final AchievementCache achievementCache;
    private final String achievementName;

    @Async
    @Transactional
    public void handle(T event) {
        Achievement achievement = getAchievement();
        if (hasAchievement(event, achievement)) {
            createProgressIfNecessary(event, achievement);
            AchievementProgress achievementProgress = getAchievementProgress(event, achievement);
            achievementProgress.increment();
            hasComplete(achievement, achievementProgress, event);
        }
    }

    private void hasComplete(Achievement achievement, AchievementProgress achievementProgress, T event) {
        if (achievement.getPoints() <= achievementProgress.getCurrentPoints()) {
            achievementService.saveAchievement(getUserAchievement(achievement, event));
        }
    }

    protected abstract boolean hasAchievement(T event, Achievement achievement);
    protected abstract void createProgressIfNecessary(T event, Achievement achievement);
    protected abstract AchievementProgress getAchievementProgress(T event, Achievement achievement);
    protected abstract UserAchievement getUserAchievement(Achievement achievement, T event);

    protected Achievement getAchievement() {
        return achievementCache.get(achievementName);
    }
}
