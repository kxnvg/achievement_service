package faang.school.achievement.messaging.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import faang.school.achievement.dto.redis.CommentEvent;
import faang.school.achievement.handler.EventHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.connection.Message;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CommentEventListenerTest {
    @InjectMocks
    CommentEventListener commentEventListener;
    @Mock
    Message message;
    @Mock
    ObjectMapper objectMapper;

    private final List<EventHandler<CommentEvent>> eventHandlers =
            List.of(Mockito.spy(EventHandler.class));

    @BeforeEach
    public void init() {
        commentEventListener = new CommentEventListener(objectMapper, eventHandlers);
    }

    @Test
    public void testOnMessage() throws IOException {
        CommentEvent mockEvent = CommentEvent.builder().authorId(1L).build();

        when(objectMapper.readValue(message.getBody(), CommentEvent.class))
                .thenReturn(mockEvent);

        commentEventListener.onMessage(message, null);

        doNothing().when(eventHandlers.get(0)).handle(mockEvent.getAuthorId());
        verify(objectMapper, Mockito.times(1)).readValue(message.getBody(), CommentEvent.class);
    }
}