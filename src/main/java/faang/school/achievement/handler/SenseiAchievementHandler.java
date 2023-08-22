package faang.school.achievement.handler;

import faang.school.achievement.dto.MentorshipEventDto;
import faang.school.achievement.repository.AchievementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SenseiAchievementHandler extends MentorshipEventHandler {

    private final AchievementRepository achievementRepository;

    @Async
    @Override
    public void handler(MentorshipEventDto eventDto) {

    }

    public void senseiAchievement() {
    }
}
