package faang.school.achievement.hundler;

import faang.school.achievement.service.AchievementProgressService;
import faang.school.achievement.service.AchievementService;
import faang.school.achievement.service.UserAchievementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class OrganizerAchievementHandler extends AbstractHandler {

    @Autowired
    public OrganizerAchievementHandler(AchievementService achievementService,
                                       UserAchievementService userAchievementService,
                                       AchievementProgressService achievementProgressService,
                                       @Value("${invite.title}") String title) {

        super(achievementService, userAchievementService, achievementProgressService, title);
    }
}
