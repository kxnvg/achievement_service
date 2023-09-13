package faang.school.achievement.handler;

import faang.school.achievement.dto.RecommendationEventDto;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.model.AchievementProgress;
import faang.school.achievement.model.UserAchievement;
import faang.school.achievement.service.AchievementProgressService;
import faang.school.achievement.service.AchievementService;
import faang.school.achievement.service.UserAchievementService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@AllArgsConstructor
@Component
public class NiceGuyAchievementHandler implements EventHandler<RecommendationEventDto> {

    private AchievementService achievementService;
    private UserAchievementService userAchievementService;
    private AchievementProgressService achievementProgressService;
    @Value("${niceGuy.title}")
    private String title;
    private final Lock lock = new ReentrantLock();

    @Override
    @Async
    public void handle(RecommendationEventDto event) {
        Achievement achievement = achievementService.getAchievement(title);

        boolean exists = userAchievementService.hasAchievement(achievement.getId(), event.getAuthorId());

        if (!exists) {
            AchievementProgress achievementProgress = achievementProgressService.getProgress(event.getAuthorId(), achievement.getId());
            achievementProgress.setUpdatedAt(null);
            achievementProgress.increment();
            achievementProgress = achievementProgressService.updateProgress(achievementProgress);

            giveAchievement(event, achievement, achievementProgress);
        }
    }

    private void giveAchievement(RecommendationEventDto event, Achievement achievement, AchievementProgress progress) {
        if (progress.getCurrentPoints() == achievement.getPoints()) {
            lock.lock();
            try {
                UserAchievement userAchievement = UserAchievement.builder()
                        .achievement(achievement)
                        .userId(event.getAuthorId())
                        .build();
                userAchievementService.giveAchievement(userAchievement);
            } finally {
                lock.unlock();
            }
        }
    }
}
