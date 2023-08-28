package faang.school.achievement.achievementHandler.invitation;

import faang.school.achievement.repository.AchievementRepository;
import faang.school.achievement.repository.cache.AchievementInMemCache;
import faang.school.achievement.service.handler.AchievementService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class OrganizerAchievementHandler extends StageInvitationAchievement {

    public OrganizerAchievementHandler(AchievementService service,
                                       AchievementInMemCache cache,
                                       @Value("${achievement.organizer}") String achievementTitle) {
        super(service, cache, achievementTitle);
    }
}

