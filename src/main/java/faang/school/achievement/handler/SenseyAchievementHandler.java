package faang.school.achievement.handler;

import faang.school.achievement.dto.redis.MentorshipStartEvent;
import org.springframework.stereotype.Component;

@Component
public class SenseyAchievementHandler implements EventHandler<MentorshipStartEvent> {
    @Override
    public boolean handle(MentorshipStartEvent event) {

        return false;
    }
}
