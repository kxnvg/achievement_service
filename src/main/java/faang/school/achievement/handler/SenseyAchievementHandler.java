package faang.school.achievement.handler;

import faang.school.achievement.cache.AchievementCache;
import faang.school.achievement.dto.redis.MentorshipStartEvent;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.model.AchievementProgress;
import faang.school.achievement.model.UserAchievement;
import faang.school.achievement.service.AchievementService;
import org.springframework.stereotype.Component;

@Component
public class SenseyAchievementHandler extends AbstractAchievementHandler<MentorshipStartEvent>
        implements EventHandler<MentorshipStartEvent> {
    public SenseyAchievementHandler(AchievementService achievementService, AchievementCache achievementCache) {
        super(achievementService, achievementCache, "Сенсей");
    }

    @Override
    public void handle(MentorshipStartEvent event) {
        super.handle(event);
    }

    @Override
    protected boolean hasAchievement(MentorshipStartEvent event, Achievement achievement) {
        return !achievementService.hasAchievement(event.getMentorId(), achievement.getId());
    }

    @Override
    protected void createProgressIfNecessary(MentorshipStartEvent event, Achievement achievement) {
        achievementService.createProgressIfNecessary(event.getMenteeId(), achievement.getId());
    }

    @Override
    protected AchievementProgress getAchievementProgress(MentorshipStartEvent event, Achievement achievement) {
        return achievementService.getAchievementProgress(event.getMenteeId(), achievement.getId());
    }

    @Override
    protected UserAchievement getUserAchievement(Achievement achievement, MentorshipStartEvent event) {
        return UserAchievement.builder()
                .achievement(achievement)
                .userId(event.getMentorId())
                .build();
    }

    @Override
    protected Achievement getAchievement() {
        return super.getAchievement();
    }
}