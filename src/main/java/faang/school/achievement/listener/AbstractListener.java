package faang.school.achievement.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import faang.school.achievement.exception.MessageReadException;
import faang.school.achievement.handler.EventHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.MessageListener;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
public abstract class AbstractListener<T> implements MessageListener {
    private final ObjectMapper objectMapper;
    private final List<EventHandler<T>> handlers;

    protected T readValue(String json, Class<T> type) {
        try {
            return objectMapper.readValue(json, type);
        } catch (IOException e) {
            log.error("Failed to read message", e);
            throw new MessageReadException(e);
        }
    }

    protected void handleEvent(T event, String achievementTitle) {
        handlers.stream()
                .filter(handler -> handler.getAchievementTitle().equals(achievementTitle))
                .forEach(handler -> handler.handle(event));
    }
}
