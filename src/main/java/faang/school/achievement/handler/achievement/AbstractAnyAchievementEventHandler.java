package faang.school.achievement.handler.achievement;

import faang.school.achievement.cache.AchievementCache;
import faang.school.achievement.handler.AbstractAchievementHandler;
import faang.school.achievement.service.AchievementService;
import org.springframework.stereotype.Component;

@Component
public abstract class AbstractAnyAchievementEventHandler<T> extends AbstractAchievementHandler<T> {
    public AbstractAnyAchievementEventHandler(AchievementCache achievementCache, AchievementService achievementService) {
        super(achievementCache, achievementService);
    }
}