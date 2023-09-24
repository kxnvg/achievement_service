package faang.school.achievement.handler;

import faang.school.achievement.cache.AchievementCache;
import faang.school.achievement.dto.ProfilePicEventDto;
import faang.school.achievement.service.AchievementService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class HandsomeHandler extends AbstractEventHandler implements EventHandler<ProfilePicEventDto> {
    public HandsomeHandler(AchievementCache achievementCache, AchievementService achievementService, AsyncTaskExecutor asyncTaskExecutor) {
        super(achievementCache, achievementService, asyncTaskExecutor);
    }

    @Override
    public void handle(ProfilePicEventDto profilePicEventDto) {
        handleAsync(profilePicEventDto.getId(), "Handsome");
    }
}