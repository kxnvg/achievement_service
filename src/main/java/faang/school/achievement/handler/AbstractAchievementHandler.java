package faang.school.achievement.handler;

import faang.school.achievement.model.Achievement;
import faang.school.achievement.model.AchievementProgress;
import faang.school.achievement.service.AchievementProgressService;
import faang.school.achievement.service.AchievementService;
import faang.school.achievement.service.UserAchievementService;
import jakarta.persistence.OptimisticLockException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.retry.annotation.Retryable;
import org.springframework.scheduling.annotation.Async;

@Slf4j
@RequiredArgsConstructor
public abstract class AbstractAchievementHandler<T> implements EventHandler<T> {

    private final AchievementService achievementService;
    private final AchievementProgressService achievementProgressService;
    private final UserAchievementService userAchievementService;
    private final String title;

    @Async
    @Retryable(retryFor = OptimisticLockException.class)
    public void handleAchievement(Long userId) {
        Achievement achievement = achievementService.getAchievementFromCache(title);

        if (userAchievementService.userHasAchievement(userId, achievement.getId())) {
            return;
        }

        AchievementProgress progress = achievementProgressService.ensureUserAchievementProgress(userId, achievement);
        achievementProgressService.incrementProgress(progress);

        if (progress.getCurrentPoints() >= achievement.getPoints()) {
            userAchievementService.giveUserAchievement(userId, achievement);
            log.info("User: {} has achieved achievement: {}", userId, achievement.getTitle());
        }
    }
}
