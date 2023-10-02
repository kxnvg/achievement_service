package faang.school.achievement.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import faang.school.achievement.dto.InviteSentEventDto;
import faang.school.achievement.handler.AbstractAchievementHandler;
import faang.school.achievement.handler.OrganizerAchievementHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.connection.Message;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InviteEventListenerTest {

    @Mock
    private ObjectMapper objectMapper;
    @Mock
    private OrganizerAchievementHandler handler;
    @Mock
    private Message message;
    private InviteSentEventDto eventDto;
    private InviteEventListener listener;

    @BeforeEach
    public void setUp() {
        List<AbstractAchievementHandler<InviteSentEventDto>> handles = List.of(handler);
        listener = new InviteEventListener(objectMapper, handles);
        eventDto = InviteSentEventDto.builder()
                .authorId(1L)
                .invitedId(2L)
                .projectId(1L)
                .build();
    }

    @Test
    void testOnMessage() throws IOException {
        byte[] body = new byte[0];
        when(message.getBody()).thenReturn(body);
        when(objectMapper.readValue(body, InviteSentEventDto.class)).thenReturn(eventDto);
        listener.onMessage(message, new byte[0]);
        verify(handler).handle(eventDto);
    }

    @Test
    void testOnMessageThrowsRTE() throws IOException {
        byte[] body = new byte[0];
        when(message.getBody()).thenReturn(body);
        when(objectMapper.readValue(body, InviteSentEventDto.class)).thenThrow(new IOException());
        assertThrows(RuntimeException.class, () -> listener.onMessage(message, new byte[0]));
    }
}