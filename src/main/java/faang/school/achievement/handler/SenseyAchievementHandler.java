package faang.school.achievement.handler;

import faang.school.achievement.cache.AchievementCache;
import faang.school.achievement.dto.redis.MentorshipStartEvent;
import faang.school.achievement.repository.AchievementProgressRepository;
import faang.school.achievement.service.AchievementService;
import org.springframework.stereotype.Component;

@Component
public class SenseyAchievementHandler extends AbstractAchievementHandler<MentorshipStartEvent>
        implements EventHandler<MentorshipStartEvent> {
    public SenseyAchievementHandler(AchievementProgressRepository achievementProgressRepository,
                                    AchievementService achievementService, AchievementCache achievementCache) {
        super(achievementProgressRepository, achievementService, achievementCache, "Сенсей");
    }
}