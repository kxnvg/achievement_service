package faang.school.achievement.handler;

import faang.school.achievement.dto.CommentEventDto;
import faang.school.achievement.service.AchievementProgressService;
import faang.school.achievement.service.AchievementService;
import faang.school.achievement.service.UserAchievementService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ExpertAchievementHandler extends AbstractHandler<CommentEventDto>{
    public ExpertAchievementHandler(AchievementService achievementService,
                               UserAchievementService userAchievementService,
                               AchievementProgressService achievementProgressService,
                               @Value("${expert.title}") String title) {
        super(achievementService, userAchievementService, achievementProgressService, title);
    }
}
