package faang.school.achievement.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import faang.school.achievement.dto.CommentEventDto;
import faang.school.achievement.handler.EventHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.connection.Message;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CommentEventListener extends AbstractListener<CommentEventDto> {
    public CommentEventListener(ObjectMapper objectMapper,
                                List<EventHandler<CommentEventDto>> eventHandlers,
                                @Value("${spring.achievements.comment.expert.title}") String title) {
        super(objectMapper, eventHandlers, title);
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        CommentEventDto event = readValue(message.getBody(), CommentEventDto.class);
        handleEvent(event);
    }
}

