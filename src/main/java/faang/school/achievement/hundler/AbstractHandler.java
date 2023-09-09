package faang.school.achievement.hundler;

import faang.school.achievement.model.Achievement;
import faang.school.achievement.model.AchievementProgress;
import faang.school.achievement.model.UserAchievement;
import faang.school.achievement.service.AchievementProgressService;
import faang.school.achievement.service.AchievementService;
import faang.school.achievement.service.UserAchievementService;
import jakarta.persistence.OptimisticLockException;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.retry.annotation.Retryable;
import org.springframework.scheduling.annotation.Async;

import java.util.Optional;

@AllArgsConstructor
public abstract class AbstractHandler implements EventHandler {

    private AchievementService achievementService;
    private UserAchievementService userAchievementService;
    private AchievementProgressService achievementProgressService;
    private String title;

    @Async
    @Override
    @Retryable(retryFor = OptimisticLockException.class)
    public void handle(Long userId) {
        Achievement achievement = achievementService.getAchievement(title);

        boolean existsAchievement = userAchievementService.hasAchievement(achievement.getId(), userId);

        if (!existsAchievement) {
            AchievementProgress achievementProgress = getAchievementProgress(userId, achievement);
            achievementProgress.setUpdatedAt(null);
            achievementProgress.increment();
            achievementProgress = achievementProgressService.updateProgress(achievementProgress);

            giveAchievement(userId, achievement, achievementProgress);
        }
    }

    @Retryable(retryFor = DataIntegrityViolationException.class)
    private AchievementProgress getAchievementProgress(Long userId, Achievement achievement) {
        Optional<AchievementProgress> progress =
                achievementProgressService.getProgress(userId, achievement.getId());

        if (progress.isEmpty()) {
            AchievementProgress achievementProgress = AchievementProgress.builder()
                    .achievement(achievement)
                    .userId(userId)
                    .currentPoints(0)
                    .build();

            return achievementProgressService.createProgress(achievementProgress);
        } else {
            return progress.get();
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
}
