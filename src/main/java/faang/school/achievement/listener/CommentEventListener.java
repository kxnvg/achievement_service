package faang.school.achievement.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import faang.school.achievement.dto.CommentEventDto;
import faang.school.achievement.dto.RecommendationEventDto;
import faang.school.achievement.handler.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CommentEventListener extends AbstractEventListener<CommentEventDto> {
    private final List<EventHandler<CommentEventDto>> eventHandlers;

    @Autowired
    public CommentEventListener(ObjectMapper objectMapper, List<EventHandler<CommentEventDto>> eventHandlers) {
        super(objectMapper);
        this.eventHandlers = eventHandlers;
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        CommentEventDto event = convertJsonToString(message, CommentEventDto.class);
        eventHandlers.forEach(handler -> handler.handle(event.getAuthorId()));
    }
}
