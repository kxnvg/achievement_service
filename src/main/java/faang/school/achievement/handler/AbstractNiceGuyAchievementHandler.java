package faang.school.achievement.handler;

import faang.school.achievement.dto.RecommendationEventDto;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.model.AchievementProgress;
import faang.school.achievement.model.UserAchievement;
import faang.school.achievement.service.AchievementProgressService;
import faang.school.achievement.service.AchievementService;
import faang.school.achievement.service.UserAchievementService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;

import java.util.Optional;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@RequiredArgsConstructor
public class AbstractNiceGuyAchievementHandler implements EventHandler<RecommendationEventDto> {

    private final AchievementService achievementService;
    private final UserAchievementService userAchievementService;
    private final AchievementProgressService achievementProgressService;
    private final String title;
    private final String description;
    private final long points;
    private final Lock lock = new ReentrantLock();

    @Override
    @Async
    public void handle(RecommendationEventDto event) {
        Achievement achievement = achievementService.getByTitle(title).orElseGet(() -> {
            Achievement achievement1 = Achievement.builder().title(title).description(description).points(points).build();
            return achievementService.createAchievement(achievement1);
        });

        boolean exists = userAchievementService.hasAchievement(achievement.getId(), event.getAuthorId());

        if (!exists) {
            AchievementProgress achievementProgress = getAchievementProgress(event, achievement);
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

    private AchievementProgress getAchievementProgress(RecommendationEventDto event, Achievement achievement) {
        Optional<AchievementProgress> progress = achievementProgressService.getProgress(event.getAuthorId(), achievement.getId());

        if (progress.isEmpty()) {
            AchievementProgress achievementProgress = AchievementProgress.builder()
                    .achievement(achievement)
                    .userId(event.getAuthorId())
                    .currentPoints(0)
                    .version(1)
                    .build();

            return achievementProgressService.createProgressIfNecessary(achievementProgress);
        } else {
            return progress.get();
        }
    }
}
