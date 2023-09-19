package faang.school.achievement.handler;

import faang.school.achievement.model.Achievement;
import faang.school.achievement.model.AchievementProgress;
import faang.school.achievement.model.UserAchievement;
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
public abstract class AbstractEventHandler<T> implements EventHandler<T> {

    private AchievementService achievementService;
    private UserAchievementService userAchievementService;
    private AchievementProgressService achievementProgressService;
    private String title;

    @Async
    @Retryable(retryFor = OptimisticLockException.class)
    public void handle(Long userId) {
        Achievement achievement = achievementService.getAchievement(title);

        boolean existsAchievement = userAchievementService.hasAchievement(userId, achievement.getId());

        if (!existsAchievement) {
            AchievementProgress achievementProgress = achievementProgressService.getProgress(userId, achievement.getId());
            achievementProgress.setUpdatedAt(null);
            achievementProgress.increment();
            achievementProgress = achievementProgressService.updateProgress(achievementProgress);

            giveAchievement(userId, achievement, achievementProgress);
        }
    }

    private void giveAchievement(Long userId, Achievement achievement, AchievementProgress progress) {
        if (progress.getCurrentPoints() == achievement.getPoints()) {

            UserAchievement userAchievement = UserAchievement.builder()
                    .achievement(achievement)
                    .userId(userId)
                    .build();

            userAchievementService.giveAchievement(userAchievement);
        }
    }


    @Async("taskExecutor")
    @Retryable(retryFor = OptimisticLockException.class)
    public void handleAchievement(Long userId) {
        Achievement achievement = achievementService.getAchievementFromCache(title);
        long achievementId = achievement.getId();

        if (userAchievementService.userHasAchievement(userId, achievementId)) {
            log.info("User: {} already has the achievement: {}", userId, title);
            return;
        }

        achievementProgressService.createProgressIfNotExist(userId, achievementId);
        AchievementProgress progress = achievementProgressService.getByUserIdAndAchievementId(userId, achievementId);
        achievementProgressService.incrementProgress(progress);

        if (progress.getCurrentPoints() == achievement.getPoints()) {
            userAchievementService.createUserAchievementIfNecessary(userId, achievementId);
            log.info("User: {} has earned the achievement: {}", userId, title);
        }
    }
}
