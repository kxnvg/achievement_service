package faang.school.achievement.messaging.follow;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j
public abstract class AbstractEventListener<T> {
    @Autowired
    private ObjectMapper objectMapper;

    public T handleEvent(Message message, Class<?> event) {
        Object objectEvent = null;
        try {
            objectEvent = objectMapper.readValue(message.getBody(), event);

        } catch (IOException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
        return (T) objectEvent;
    }
}
