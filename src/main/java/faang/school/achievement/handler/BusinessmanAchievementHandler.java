package faang.school.achievement.handler;

import faang.school.achievement.dto.ProjectEventDto;
import faang.school.achievement.publisher.AchievementEventPublisher;
import faang.school.achievement.service.AchievementProgressService;
import faang.school.achievement.service.AchievementService;
import faang.school.achievement.service.UserAchievementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class BusinessmanAchievementHandler extends AbstractAchievementHandler<ProjectEventDto> {

    @Autowired
    public BusinessmanAchievementHandler(AchievementService achievementService,
                                         AchievementProgressService achievementProgressService,
                                         UserAchievementService userAchievementService,
                                         @Value("${businessman.title}") String title,
                                         AchievementEventPublisher achievementEventPublisher) {
        super(achievementService, achievementProgressService, userAchievementService, achievementEventPublisher,title);
    }

    @Override
    public void handle(ProjectEventDto event) {
        handleAchievement(event.getOwnerId());
    }
}
