package faang.school.achievement.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import faang.school.achievement.dto.ProjectEventDto;
import faang.school.achievement.handler.EventHandler;
import lombok.NonNull;
import org.springframework.data.redis.connection.Message;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProjectEventListener extends AbstractEventListener<ProjectEventDto> {

    public ProjectEventListener(ObjectMapper objectMapper,
                                List<EventHandler<ProjectEventDto>> eventHandlers) {
        super(objectMapper, eventHandlers);
    }

    @Override
    public void onMessage(@NonNull Message message, byte[] pattern) {
        ProjectEventDto projectEventDto = deserializeJsonToEvent(message, ProjectEventDto.class);
        handlers.forEach(handler -> handler.handle(projectEventDto));
    }
}
