package faang.school.achievement.service.handler.followHandler;

import faang.school.achievement.dto.follow.FollowEventDto;
import faang.school.achievement.repository.AchievementCache;
import faang.school.achievement.service.handler.AchievementService;
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
    private final AchievementCache achievementCache;
    @Value("${achievements.celebrity_achievement}")
    private String achievementName;

    @Override
    @Async("eventHandlerExecutor")
    public void handle(FollowEventDto event) {
        var achievement = achievementCache.getAchievement(achievementName);
        achievementService.updateAchievementProgress(event.getTargetUserId(), achievement);
        log.info(event + " has been handled successfully");
    }
}
