package faang.school.achievement.handler.skill;

import faang.school.achievement.cache.AchievementCache;
import faang.school.achievement.dto.SkillAcquiredEventDto;
import faang.school.achievement.handler.AbstractAchievementHandler;
import faang.school.achievement.service.AchievementService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SkillAcquiredEventHandler extends AbstractAchievementHandler<SkillAcquiredEventDto> {
    @Value("${spring.achievements.skill.title}")
    private String achievementTitle;

    public SkillAcquiredEventHandler(AchievementCache achievementCache, AchievementService achievementService) {
        super(achievementCache, achievementService);
    }

    @Override
    public String getAchievementTitle() {
        return achievementTitle;
    }

    @Override
    public void handle(SkillAcquiredEventDto event) {
        handleAchievement(achievementTitle, event.getReceiverId());
    }
}
