package faang.school.achievement.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import faang.school.achievement.dto.ProjectDto;
import faang.school.achievement.dto.ProjectEventDto;
import faang.school.achievement.handler.EventHandler;
import org.springframework.data.redis.connection.Message;

import java.util.List;

public class ProjectEventListener extends AbstractEventListener<ProjectEventDto> {
    private final List<EventHandler> eventHandlers;

    public ProjectEventListener(ObjectMapper objectMapper, List<EventHandler> eventHandlers) {
        super(objectMapper);
        this.eventHandlers = eventHandlers;
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        ProjectEventDto projectEventDto = convertJsonToString(message, ProjectEventDto.class);
        eventHandlers.forEach(handler -> handler.handle(projectEventDto));
    }
}
