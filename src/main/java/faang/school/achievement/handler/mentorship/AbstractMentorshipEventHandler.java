package faang.school.achievement.handler.mentorship;

import faang.school.achievement.cache.AchievementCache;
import faang.school.achievement.handler.AbstractAchievementHandler;
import faang.school.achievement.service.AchievementService;
import org.springframework.stereotype.Component;

@Component
public abstract class AbstractMentorshipEventHandler<T> extends AbstractAchievementHandler<T> {
    public AbstractMentorshipEventHandler(AchievementCache achievementCache, AchievementService achievementService) {
        super(achievementCache, achievementService);
    }
}