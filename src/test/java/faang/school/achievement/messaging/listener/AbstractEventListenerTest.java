package faang.school.achievement.messaging.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import faang.school.achievement.dto.redis.CommentEvent;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.connection.Message;

import java.io.IOException;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class AbstractEventListenerTest {
    @InjectMocks
    AbstractEventListener<CommentEvent> abstractEventListener;
    @Mock
    Message message;
    @Mock
    private ObjectMapper objectMapper;

    @Test
    public void testEventMapper() throws IOException {
        abstractEventListener.eventMapper(message, CommentEvent.class);

        verify(objectMapper, times(1)).readValue(message.getBody(), CommentEvent.class);
    }
}
