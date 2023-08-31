package faang.school.achievement.handler;


import faang.school.achievement.service.AchievementProgressService;
import faang.school.achievement.service.AchievementService;
import faang.school.achievement.service.UserAchievementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class NiceGuyAchievementHandler extends AbstractNiceGuyAchievementHandler {

    @Autowired
    public NiceGuyAchievementHandler(AchievementService achievementService,
                                     UserAchievementService userAchievementService,
                                     AchievementProgressService achievementProgressService,
                                     @Value("${achievement.invite.title}") @SuppressWarnings("unused") String title,
                                     @Value("${achievement.invite.description}") @SuppressWarnings("unused") String description,
                                     @Value("${achievement.invite.points}") @SuppressWarnings("unused") long points) {
        super(achievementService, userAchievementService, achievementProgressService, title, description, points);
    }
}
