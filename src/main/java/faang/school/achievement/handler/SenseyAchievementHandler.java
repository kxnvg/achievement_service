package faang.school.achievement.handler;

import faang.school.achievement.cache.AchievementCache;
import faang.school.achievement.dto.AchievementDto;
import faang.school.achievement.dto.redis.MentorshipStartEvent;
import faang.school.achievement.mapper.AchievementMapper;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.service.AchievementService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SenseyAchievementHandler implements EventHandler<MentorshipStartEvent> {
    private static final String ACHIEVEMENT_NAME = "Сенсей";
    private final AchievementService achievementService;
    private final AchievementCache achievementCache;
    private final AchievementMapper achievementMapper;

    @Override
    public boolean handle(MentorshipStartEvent event) {
        AchievementDto achievementDto = getAchievementDto();
        if (hasAchievement(event, achievementDto)) {

        }
        return false;
    }

    private boolean hasAchievement(MentorshipStartEvent event, AchievementDto achievementDto) {
        return !achievementService.hasAchievement(event.getMentorId(), achievementDto.getId());
    }

    private AchievementDto getAchievementDto() {
        return achievementMapper.toDto(achievementCache.get(ACHIEVEMENT_NAME));
    }
}
