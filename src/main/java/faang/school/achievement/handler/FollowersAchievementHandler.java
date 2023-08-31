package faang.school.achievement.handler;

import faang.school.achievement.cache.AchievementCache;
import faang.school.achievement.dto.FollowerEventDto;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.model.AchievementProgress;
import faang.school.achievement.service.AchievementService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@Setter
@RequiredArgsConstructor
public class FollowersAchievementHandler extends FollowerEventHandler {

    private final AchievementService achievementService;
    private final AchievementCache achievementCache;

    @Value("${achievement-service.achievement.followers.name}")
    private String followersAchievementName;

    @Async("followerHandlerThreadPoolExecutor")
    @Override
    public void handle(FollowerEventDto event) {
        Achievement achievement = achievementCache.get(followersAchievementName)
                .or(() -> achievementService.getAchievement(followersAchievementName))
                .orElseThrow(() -> new EntityNotFoundException(String.format("There is no achievement named: %s", followersAchievementName)));

        long userId = event.getFolloweeId();
        long achievementId = achievement.getId();

        if (!achievementService.hasAchievement(achievementId, userId)) {
            achievementService.checkAndCreateAchievementProgress(userId, achievementId);
        }
        long currentProgress = achievementService.getProgress(userId, achievementId);

        if (currentProgress >= achievement.getPoints()) {
            achievementService.giveAchievement(userId, achievementId);
        }
    }
}