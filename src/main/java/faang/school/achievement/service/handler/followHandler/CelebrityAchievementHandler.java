package faang.school.achievement.service.handler.followHandler;

import faang.school.achievement.dto.follow.FollowEventDto;
import faang.school.achievement.repository.cache.AchievementInMemCache;
import faang.school.achievement.service.AchievementService;
import faang.school.achievement.service.handler.EventHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class CelebrityAchievementHandler implements EventHandler<FollowEventDto> {
    private final AchievementService achievementService;
    private final AchievementInMemCache achievementInMemCache;
    @Value("${achievements.celebrity_achievement}")
    private String achievementName;

    @Override
    @Async("eventHandlerExecutor")
    public void handle(FollowEventDto event) {
        achievementInMemCache.getAchievement(achievementName).ifPresentOrElse(
                achievement -> {
                    achievementService.updateAchievementProgress(event.getTargetUserId(), achievement);
                    log.info(event + " has been handled successfully");
                },
                () -> log.warn("Achievement '{}' not found in cache.", achievementName)
        );
    }
}
