package faang.school.achievement.handler;

import faang.school.achievement.dto.EventDto;
import org.springframework.stereotype.Component;

@Component
public abstract class PostAchievementHandler implements EventHandler {

    @Override
    public void handle(EventDto eventDto) {
    }
}
