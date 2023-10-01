package faang.school.achievement.handler;

import faang.school.achievement.dto.PostEventDto;
import faang.school.achievement.service.AchievementProgressService;
import faang.school.achievement.service.AchievementService;
import faang.school.achievement.service.UserAchievementService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class OpinionLeaderAchievementHandler extends AbstractHandler<PostEventDto>{
    public OpinionLeaderAchievementHandler(AchievementService achievementService,
                                           UserAchievementService userAchievementService,
                                           AchievementProgressService achievementProgressService,
                                           @Value("${post.title}") String title) {
        super(achievementService, userAchievementService, achievementProgressService, title);
    }
}
