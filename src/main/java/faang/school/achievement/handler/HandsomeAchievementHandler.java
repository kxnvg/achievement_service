package faang.school.achievement.handler;

import faang.school.achievement.cache.AchievementCache;
import faang.school.achievement.dto.redis.ProfilePicEvent;
import faang.school.achievement.messaging.publisher.AchievementEventPublisher;
import faang.school.achievement.repository.AchievementProgressRepository;
import faang.school.achievement.service.AchievementService;

public class HandsomeAchievementHandler extends AbstractAchievementHandler<ProfilePicEvent>
        implements EventHandler<ProfilePicEvent> {

    public HandsomeAchievementHandler(AchievementProgressRepository achievementProgressRepository,
                                      AchievementService achievementService, AchievementCache achievementCache,
                                      AchievementEventPublisher achievementEventPublisher, String achievementName) {

        super(achievementProgressRepository, achievementService, achievementCache, achievementEventPublisher,
                "Красавчик");
    }
}
