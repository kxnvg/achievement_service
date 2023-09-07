package faang.school.achievement.handler;

import faang.school.achievement.dto.FollowerEventDto;
import faang.school.achievement.service.AchievementService;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@Setter
public class FollowersAchievementHandler extends AbstractAchievementHandler<FollowerEventDto> {

    @Value("${achievement-service.achievement.followers.name}")
    private String followersAchievementName;

    public FollowersAchievementHandler(AchievementService achievementService) {
        super(achievementService);
    }

    @Async("followerHandlerThreadPoolExecutor")
    @Override
    public void handle(FollowerEventDto event) {
        long userId = event.getFolloweeId();
        handleAchievement(userId, followersAchievementName);
    }
}