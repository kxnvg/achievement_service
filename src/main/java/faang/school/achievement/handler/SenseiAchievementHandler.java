package faang.school.achievement.handler;

import faang.school.achievement.dto.MentorshipEventDto;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.model.AchievementProgress;
import faang.school.achievement.service.AchievementService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SenseiAchievementHandler extends MentorshipEventHandler {

    private final AchievementService achievementService;
    private final AchievementProgress achievementProgress;
    private final Achievement achievement;

    @Async
    @Override
    public void handler(MentorshipEventDto eventDto) {
    }

    public void senseiAchievement() {
        long userId = achievementProgress.getUserId();
        long achievementId = achievement.getId();
        boolean hasAchievement = achievementService.hasAchievement(userId, achievementId);
        if (!hasAchievement) {
            achievementService.createAchievementProgress(userId, achievement);
        }
        achievementService.getProgress(userId, achievementId);
    }
}
