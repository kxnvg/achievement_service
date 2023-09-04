package faang.school.achievement.handler;

import faang.school.achievement.cache.AchievementCache;
import faang.school.achievement.dto.TaskEventDto;
import faang.school.achievement.service.AchievementService;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.stereotype.Component;

@Component
public class MrProductivityAchievementHandler extends AbstractEventHandler implements EventHandler<TaskEventDto> {

    public MrProductivityAchievementHandler(AchievementCache achievementCache, AchievementService achievementService, AsyncTaskExecutor asyncTaskExecutor) {
        super(achievementCache, achievementService, asyncTaskExecutor);
    }

    @Override
    public void handle(TaskEventDto value) {
        handleAsync(value.getUserId(), "MrProductivity");
    }
}
