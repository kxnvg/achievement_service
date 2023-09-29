package faang.school.achievement.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import faang.school.achievement.dto.CommentEventDto;
import faang.school.achievement.handler.EventHandler;
import org.springframework.data.redis.connection.Message;

import java.util.List;

public class CommentEventListener extends AbstractListener<CommentEventDto> {
    public CommentEventListener(ObjectMapper objectMapper,
                                List<EventHandler<CommentEventDto>> eventHandlers,
                                String title) {
        super(objectMapper, eventHandlers, title);
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        CommentEventDto event = readValue(message.getBody(), CommentEventDto.class);
        handleEvent(event);
    }
}
}
