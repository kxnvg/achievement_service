package faang.school.achievement.message.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import faang.school.achievement.dto.CommentDto;
import faang.school.achievement.message.handler.CommentEventHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentEventListener implements MessageListener {
    private final ObjectMapper objectMapper;
    private final List<CommentEventHandler> commentEventHandlers;

    @Override
    public void onMessage(Message message, byte[] pattern) {
        CommentDto commentDto;
        try {
            commentDto = objectMapper.readValue(message.getBody(), CommentDto.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Long authorId = commentDto.getAuthorId();
        commentEventHandlers.stream().peek(handler -> handler.createProgressIfNecessary(authorId))
                .filter(handler -> !handler.isCompleted(authorId))
                .forEach(handler -> handler.addPoint(authorId));
    }
}
