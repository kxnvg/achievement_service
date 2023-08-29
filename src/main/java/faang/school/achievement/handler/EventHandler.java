package faang.school.achievement.handler;

import faang.school.achievement.dto.EventDto;

public interface EventHandler {

    void handle(EventDto eventDto);
}
