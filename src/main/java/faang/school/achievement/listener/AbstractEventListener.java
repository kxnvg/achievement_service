package faang.school.achievement.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import faang.school.achievement.dto.ProjectEventDto;
import faang.school.achievement.exception.DeserializeJSONException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;

import java.io.IOException;

@RequiredArgsConstructor
public abstract class AbstractEventListener<T> implements MessageListener {
    protected final ObjectMapper objectMapper;

    protected T convertJsonToString(Message message, Class<T> type) {
        try {
            return objectMapper.readValue(message.getBody(), type);
        } catch (IOException e) {
            throw new DeserializeJSONException("Could not deserialize event");
        }
    }
}
