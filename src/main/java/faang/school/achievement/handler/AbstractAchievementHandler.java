package faang.school.achievement.handler;

import faang.school.achievement.cache.AchievementCache;
import faang.school.achievement.messaging.publisher.AchievementEventPublisher;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.model.AchievementProgress;
import faang.school.achievement.model.UserAchievement;
import faang.school.achievement.repository.AchievementProgressRepository;
import faang.school.achievement.service.AchievementService;
import jakarta.persistence.PersistenceException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.scheduling.annotation.Async;

@RequiredArgsConstructor
public abstract class AbstractAchievementHandler<T> implements EventHandler<T> {
    protected final AchievementProgressRepository achievementProgressRepository;
    protected final AchievementService achievementService;
    protected final AchievementCache achievementCache;

    private final AchievementEventPublisher achievementEventPublisher;
    private final String achievementName;

    @Async
    @Transactional
    @Retryable(maxAttempts = 5, backoff = @Backoff(delay = 10), retryFor = PersistenceException.class)
    public void handle(Long userId) {
        Achievement achievement = getAchievement();
        if (!hasAchievement(userId, achievement)) {
            createProgressIfNecessary(userId, achievement);
            AchievementProgress achievementProgress = getAchievementProgress(userId, achievement);
            achievementProgress.increment();

            achievementProgressRepository.save(achievementProgress);
            hasComplete(achievement, achievementProgress, userId);
        }
    }

    private void hasComplete(Achievement achievement, AchievementProgress achievementProgress, Long userId) {
        if (achievement.getPoints() <= achievementProgress.getCurrentPoints()) {
            UserAchievement userAchievement = getUserAchievement(achievement, userId);
            achievementService.saveAchievement(userAchievement);
            achievementEventPublisher.publish(userAchievement);
        }
    }

    private boolean hasAchievement(Long userId, Achievement achievement) {
        return achievementService.hasAchievement(userId, achievement.getId());
    }
    private void createProgressIfNecessary(Long userId, Achievement achievement) {
        achievementService.createProgressIfNecessary(userId, achievement.getId());
    }

    private AchievementProgress getAchievementProgress(Long userId, Achievement achievement) {
        return achievementService.getAchievementProgress(userId, achievement.getId());
    }

    private UserAchievement getUserAchievement(Achievement achievement, Long userId) {
        return UserAchievement.builder()
                .achievement(achievement)
                .userId(userId)
                .build();
    }

    private Achievement getAchievement() {
        return achievementCache.get(achievementName);
    }
}
