package faang.school.achievement.handler.mentorship;

import faang.school.achievement.cache.AchievementCache;
import faang.school.achievement.dto.MentorshipStartEventDto;
import faang.school.achievement.service.AchievementService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SenseiHandler extends AbstractMentorshipEventHandler<MentorshipStartEventDto> {

    @Value("${spring.achievements.mentorship.sensei.title}")
    private String achievementTitle;

    public SenseiHandler(AchievementCache achievementCache, AchievementService achievementService) {
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