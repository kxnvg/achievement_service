package faang.school.achievement.handler;

import faang.school.achievement.achievement_test.AchievementCache;
import faang.school.achievement.dto.FollowerEventDto;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.model.AchievementProgress;
import faang.school.achievement.service.AchievementService;
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
        Achievement achievement = achievementCache.get(followersAchievementName);
        long userId = event.getFolloweeId();

        if (!achievementService.hasAchievement(achievement, userId)) {
            AchievementProgress userAchievementProgress = achievementService.getUserProgressByAchievementAndUserId(achievement.getId(), userId);
            userAchievementProgress.increment();
            achievementService.addAchievementToUserIfEnoughPoints(userAchievementProgress, achievement, userId);
        }
    }
}