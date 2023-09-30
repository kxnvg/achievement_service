package faang.school.achievement.handler;

import faang.school.achievement.dto.RecommendationEventDto;
import faang.school.achievement.service.AchievementProgressService;
import faang.school.achievement.service.AchievementService;
import faang.school.achievement.service.UserAchievementService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class NiceGuyAchievementHandler extends AbstractHandler<RecommendationEventDto> {

    public NiceGuyAchievementHandler(AchievementService achievementService,
                                     UserAchievementService userAchievementService,
                                     AchievementProgressService achievementProgressService,
                                     @Value("${niceGuy.title}") String title) {
        super(achievementService, userAchievementService, achievementProgressService, title);
    }
}
