package faang.school.achievement.service.handler.SenseyAchievementHandler;

import faang.school.achievement.dto.follow.Mentorship.MentorshipEventDto;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.repository.AchievementCache;
import faang.school.achievement.service.handler.AchievementService;
import faang.school.achievement.service.handler.EventHandler;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@Slf4j
public class SenseyAchievementHandler implements EventHandler<MentorshipEventDto> {
    private final AchievementService service;
    private final AchievementCache achievementCache;
    @Value("${achievements.sensey_achievement}")
    private String achievementName;

    @Override
    @Async("eventHandlerExecutor")
    public void handle(MentorshipEventDto mentorshipEventDto) {
        Achievement achievement = achievementCache.getAchievement(achievementName);
        service.updateAchievementProgress(mentorshipEventDto.getUserId(), achievement);
        log.info(mentorshipEventDto + "successfully handled");
    }
}
