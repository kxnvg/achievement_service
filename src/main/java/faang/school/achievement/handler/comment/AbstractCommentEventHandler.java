package faang.school.achievement.handler.comment;

import faang.school.achievement.cache.AchievementCache;
import faang.school.achievement.handler.AbstractAchievementHandler;
import faang.school.achievement.service.AchievementService;
import org.springframework.stereotype.Component;

@Component
public abstract class AbstractCommentEventHandler<T> extends AbstractAchievementHandler<T> {
    public AbstractCommentEventHandler(AchievementCache achievementCache, AchievementService achievementService) {
        super(achievementCache, achievementService);
    }
}
