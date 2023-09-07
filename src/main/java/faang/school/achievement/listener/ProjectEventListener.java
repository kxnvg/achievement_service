package faang.school.achievement.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import faang.school.achievement.dto.ProjectEventDto;
import faang.school.achievement.handler.EventHandler;
import org.springframework.data.redis.connection.Message;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProjectEventListener extends AbstractEventListener<ProjectEventDto> {
    private final List<EventHandler<ProjectEventDto>> eventHandlers;

    public ProjectEventListener(ObjectMapper objectMapper,
                                List<EventHandler<ProjectEventDto>> eventHandlers) {
        super(objectMapper);
        this.eventHandlers = eventHandlers;
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        ProjectEventDto projectEventDto = convertJsonToString(message, ProjectEventDto.class);
        eventHandlers.stream()
                .filter(handler -> handler.examinationEvent(projectEventDto))
                .forEach(handler -> handler.handle(projectEventDto));
    }
}
