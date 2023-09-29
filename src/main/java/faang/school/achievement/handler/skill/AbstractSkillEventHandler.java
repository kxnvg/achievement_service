package faang.school.achievement.handler.skill;

import faang.school.achievement.cache.AchievementCache;
import faang.school.achievement.handler.AbstractAchievementHandler;
import faang.school.achievement.service.AchievementService;
import org.springframework.stereotype.Component;

@Component
public abstract class AbstractSkillEventHandler<T> extends AbstractAchievementHandler<T> {
    public AbstractSkillEventHandler(AchievementCache achievementCache, AchievementService achievementService) {
        super(achievementCache, achievementService);
    }
}