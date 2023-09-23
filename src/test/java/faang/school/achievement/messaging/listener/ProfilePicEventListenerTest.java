package faang.school.achievement.messaging.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import faang.school.achievement.dto.redis.CommentEvent;
import faang.school.achievement.dto.redis.ProfilePicEvent;
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
public class ProfilePicEventListenerTest {

    @Mock
    Message message;
    @Mock
    ObjectMapper objectMapper;

    @InjectMocks
    ProfilePicEventListener profilePicEventListener;

    private final List<EventHandler<ProfilePicEvent>> eventHandlers =
            List.of(Mockito.spy(EventHandler.class));

    @BeforeEach
    public void init() {
        profilePicEventListener = new ProfilePicEventListener(objectMapper, eventHandlers);
    }

    @Test
    public void testOnMessage() {
        ProfilePicEvent event = ProfilePicEvent.builder().userId(1L).build();

        when(profilePicEventListener.mapEvent(message, ProfilePicEvent.class)).thenReturn(event);

        profilePicEventListener.onMessage(message, null);

        verify(eventHandlers.get(0)).handle(event.getUserId());
    }
}
