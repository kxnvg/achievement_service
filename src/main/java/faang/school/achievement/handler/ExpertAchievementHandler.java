package faang.school.achievement.handler;

import faang.school.achievement.cache.AchievementCache;
import faang.school.achievement.dto.redis.CommentEvent;
import faang.school.achievement.messaging.publisher.AchievementEventPublisher;
import faang.school.achievement.repository.AchievementProgressRepository;
import faang.school.achievement.service.AchievementService;
import org.springframework.stereotype.Component;

@Component
public class ExpertAchievementHandler extends AbstractAchievementHandler<CommentEvent>
        implements EventHandler<CommentEvent> {
    public ExpertAchievementHandler(AchievementProgressRepository achievementProgressRepository,
                                    AchievementService achievementService, AchievementCache achievementCache,
                                    AchievementEventPublisher achievementEventPublisher) {
        super(achievementProgressRepository, achievementService, achievementCache,
                achievementEventPublisher, "Эксперт");
    }
}