package faang.school.achievement.handler;

import faang.school.achievement.cache.AchievementCache;
import faang.school.achievement.dto.InviteSentEventDto;
import faang.school.achievement.service.AchievementService;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.stereotype.Component;

@Component
public class OrganizerAchievementHandler extends AbstractEventHandler implements EventHandler<InviteSentEventDto> {
    public OrganizerAchievementHandler(AchievementCache achievementCache, AchievementService achievementService, AsyncTaskExecutor asyncTaskExecutor) {
        super(achievementCache, achievementService, asyncTaskExecutor);
    }

    @Override
    public void handle(InviteSentEventDto value) {
        handleAsync(value.getAuthorId(), "Organizer");
    }
}
