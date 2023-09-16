package faang.school.achievement.messaging.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import faang.school.achievement.dto.redis.MentorshipStartEvent;
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

@ExtendWith(MockitoExtension.class)
public class MentorshipStartListenerTest {
    @Mock
    private Message message;
    @Mock
    private ObjectMapper objectMapper;
    @InjectMocks
    private MentorshipStartListener mentorshipStartListener;

    private final List<EventHandler<MentorshipStartEvent>> eventHandlers =
            List.of(Mockito.spy(EventHandler.class));

    @BeforeEach
    public void init() {
        mentorshipStartListener = new MentorshipStartListener(objectMapper, eventHandlers);
    }

    @Test
    public void testOnMessage() throws IOException {
        MentorshipStartEvent event = new MentorshipStartEvent();
        event.setMentorId(0L);

        Mockito.when(objectMapper.readValue(message.getBody(), MentorshipStartEvent.class))
                .thenReturn(event);
        mentorshipStartListener.onMessage(message, null);

        Mockito.doNothing().when(eventHandlers.get(0)).handle(event.getMentorId());
        Mockito.verify(objectMapper).readValue(message.getBody(), MentorshipStartEvent.class);
    }
}
