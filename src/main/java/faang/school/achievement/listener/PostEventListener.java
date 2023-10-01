package faang.school.achievement.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import faang.school.achievement.dto.PostEventDto;
import faang.school.achievement.handler.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class PostEventListener extends AbstractEventListener<PostEventDto> {
    private final List<EventHandler<PostEventDto>> eventHandlers;

    @Autowired
    public PostEventListener(ObjectMapper objectMapper, List<EventHandler<PostEventDto>> eventHandlers) {
        super(objectMapper);
        this.eventHandlers = eventHandlers;
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        PostEventDto event = convertJsonToString(message, PostEventDto.class);
        eventHandlers.forEach(handler -> handler.handle(event.getAuthorId()));
    }
}
