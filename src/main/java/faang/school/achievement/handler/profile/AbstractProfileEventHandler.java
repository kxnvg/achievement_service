package faang.school.achievement.handler.profile;

import faang.school.achievement.cache.AchievementCache;
import faang.school.achievement.dto.ProfilePicEventDto;
import faang.school.achievement.handler.AbstractAchievementHandler;
import faang.school.achievement.service.AchievementService;
import org.springframework.stereotype.Component;

@Component
public abstract class AbstractProfileEventHandler<T> extends AbstractAchievementHandler<T> {
    public AbstractProfileEventHandler(AchievementCache achievementCache, AchievementService achievementService) {
        super(achievementCache, achievementService);
    }
}
