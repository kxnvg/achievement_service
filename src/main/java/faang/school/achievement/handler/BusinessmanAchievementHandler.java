package faang.school.achievement.handler;

import faang.school.achievement.dto.ProjectEventDto;
import faang.school.achievement.service.AchievementProgressService;
import faang.school.achievement.service.AchievementService;
import faang.school.achievement.service.UserAchievementService;
import org.springframework.stereotype.Component;

@Component
public class BusinessmanAchievementHandler extends AbstractEventHandler<ProjectEventDto> {


    public BusinessmanAchievementHandler(AchievementService achievementService,
                                         AchievementProgressService achievementProgressService,
                                         UserAchievementService userAchievementService,
                                         String title) {
        super(achievementService, achievementProgressService, userAchievementService, title);
    }

    @Override
    public void handle(ProjectEventDto event) {
        handleAchievement(event.getProjectId());
    }
}
