package faang.school.achievement.achievementHandler.invitation;

import faang.school.achievement.dto.invitation.StageInvitationEvent;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.repository.cache.AchievementInMemCache;
import faang.school.achievement.service.handler.AchievementService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class OrganizerAchievementHandler extends StageInvitationAchievement {
    private final AchievementService service;
    private final AchievementInMemCache cache;
    @Value("${achievement.organizer}")
    private String achievementTitle;

    @Override
    @Async
    public void process(StageInvitationEvent event) {
        Achievement achievement = cache.getAchievement(achievementTitle)
                .orElseThrow(() -> new RuntimeException("Achievement not found"));
        service.updateAchievementProgress(event.getAuthorId(), achievement);
    }
}

