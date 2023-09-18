package faang.school.achievement.handler;

import faang.school.achievement.cache.AchievementCache;
import faang.school.achievement.dto.MentorshipStartEventDto;
import faang.school.achievement.service.AchievementService;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.stereotype.Component;

@Component
public class SenseyAchievementHandler extends AbstractEventHandler implements EventHandler<MentorshipStartEventDto> {
    public SenseyAchievementHandler(AchievementCache achievementCache, AchievementService achievementService, AsyncTaskExecutor asyncTaskExecutor) {
        super(achievementCache, achievementService, asyncTaskExecutor);
    }

    @Override
    public void handle(MentorshipStartEventDto value) {
        handleAsync(value.getRequesterId(), "Super Mentor");
    }
}
