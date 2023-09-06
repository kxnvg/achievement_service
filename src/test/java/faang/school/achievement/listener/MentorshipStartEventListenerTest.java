package faang.school.achievement.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import faang.school.achievement.dto.MentorshipStartEventDto;
import faang.school.achievement.handler.EventHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.data.redis.connection.Message;

import java.io.IOException;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class MentorshipStartEventListenerTest {
    @InjectMocks
    private MentorshipStartEventListener mentorshipStartEventListener;
    @Mock
    private ObjectMapper objectMapper;
    @Mock
    private List<EventHandler<MentorshipStartEventDto>> handlers;
    private MentorshipStartEventDto mentorshipStartEventDto;
    private Message message;

    @BeforeEach
    void setUp() throws IOException {
        mentorshipStartEventDto = mock(MentorshipStartEventDto.class);

        message = mock(Message.class);
        byte[] body = new byte[0];

        when(message.getBody()).thenReturn(body);
        when(objectMapper.readValue(message.getBody(), MentorshipStartEventDto.class))
                .thenReturn(mentorshipStartEventDto);
    }

    @Test
    void onMessage_shouldInvokeObjectMapperReadValueMethod() throws IOException {
        mentorshipStartEventListener.onMessage(message, new byte[0]);
        verify(objectMapper).readValue(message.getBody(), MentorshipStartEventDto.class);
    }

    @Test
    void onMessage_shouldInvokeHandleEventMethod() {
        mentorshipStartEventListener.onMessage(message, new byte[0]);
        handlers.forEach(handler -> verify(handler).handle(mentorshipStartEventDto));
    }
}