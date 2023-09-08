package faang.school.achievement.handler.mentorship;

import faang.school.achievement.cache.AchievementCache;
import faang.school.achievement.dto.MentorshipStartEventDto;
import faang.school.achievement.handler.AbstractAchievementHandler;
import faang.school.achievement.service.AchievementService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SenseiAchievementEventHandler extends AbstractAchievementHandler<MentorshipStartEventDto> {

    @Value("${spring.achievements.mentorship.title}")
    private String achievementTitle;

    public SenseiAchievementEventHandler(AchievementCache achievementCache, AchievementService achievementService) {
        super(achievementCache, achievementService);
    }

    @Override
    public void handle(MentorshipStartEventDto event) {
        handleAchievement(achievementTitle, event.getReceiverId());
    }

    @Override
    public String getAchievementTitle() {
        return achievementTitle;
    }
}