package faang.school.achievement.handler;

import faang.school.achievement.cache.AchievementCache;
import faang.school.achievement.dto.RecommendationEventDto;
import faang.school.achievement.service.AchievementProgressService;
import faang.school.achievement.service.AchievementService;
import faang.school.achievement.service.UserAchievementService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class NiceGuyAchievementHandler extends AbstractAchievementHandler<RecommendationEventDto> {

    public NiceGuyAchievementHandler(AchievementService achievementService,
                                     AchievementProgressService achievementProgressService,
                                     UserAchievementService userAchievementService,
                                     AchievementCache achievementCache,
                                     @Value("${niceGuy.title}") String title) {
        super(achievementService, achievementProgressService, userAchievementService, achievementCache, title);
    }

    @Override
    public void handle(RecommendationEventDto event) {
        handleAchievement(event.getReceiverId());
    }
}
