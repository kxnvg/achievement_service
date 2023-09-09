package faang.school.achievement.handler.achievement;

import faang.school.achievement.cache.AchievementCache;
import faang.school.achievement.dto.AchievementEventDto;
import faang.school.achievement.handler.AbstractAchievementHandler;
import faang.school.achievement.service.AchievementService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class RecursionAchievementEventHandler extends AbstractAchievementHandler<AchievementEventDto> {

    @Value("${spring.achievements.achievement.title}")
    private String achievementTitle;

    public RecursionAchievementEventHandler(AchievementCache achievementCache, AchievementService achievementService) {
        super(achievementCache, achievementService);
    }

    @Override
    public void handle(AchievementEventDto event) {
        handleAchievement(achievementTitle, event.getAuthorId());
    }

    @Override
    public String getAchievementTitle() {
        return achievementTitle;
    }
}