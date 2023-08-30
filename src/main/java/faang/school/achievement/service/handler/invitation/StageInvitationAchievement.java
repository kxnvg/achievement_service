package faang.school.achievement.service.handler.invitation;

import faang.school.achievement.dto.invitation.StageInvitationEvent;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.repository.cache.AchievementInMemCache;
import faang.school.achievement.service.AchievementService;
import faang.school.achievement.service.handler.EventHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;

@RequiredArgsConstructor
public abstract class StageInvitationAchievement implements EventHandler<StageInvitationEvent> {
    private final AchievementService service;
    private final AchievementInMemCache cache;
    private final String achievementTitle;

    @Override
    @Async
    public void handle(StageInvitationEvent event) {
        Achievement achievement = cache.getAchievement(achievementTitle)
                .orElseThrow(() -> new RuntimeException("Achievement not found"));
        service.updateAchievementProgress(event.getAuthorId(), achievement);
    }
}
