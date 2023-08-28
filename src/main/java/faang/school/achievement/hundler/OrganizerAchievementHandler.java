package faang.school.achievement.hundler;

import faang.school.achievement.service.AchievementProgressService;
import faang.school.achievement.service.AchievementService;
import faang.school.achievement.service.UserAchievementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class OrganizerAchievementHandler extends AbstractOrganizerAchievementHandler {

    @Autowired
    public OrganizerAchievementHandler(AchievementService achievementService,
                                       UserAchievementService userAchievementService,
                                       AchievementProgressService achievementProgressService,
                                       @Value("${invite.title}") String title,
                                       @Value("${invite.description}") String description,
                                       @Value("${invite.points}") long points) {

        super(achievementService, userAchievementService, achievementProgressService, title, description, points);
    }
}
