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

import java.util.List;

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
    public void testOnMessage() {
        CommentEvent event = CommentEvent.builder().authorId(1L).build();

        when(commentEventListener.mapEvent(message, CommentEvent.class)).thenReturn(event);

        commentEventListener.onMessage(message, null);

        verify(eventHandlers.get(0)).handle(event.getAuthorId());
    }
}
