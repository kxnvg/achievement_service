package faang.school.achievement.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import faang.school.achievement.dto.AchievementEventDto;
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

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class AchievementEventListenerTest {
    @InjectMocks
    private AchievementEventListener achievementEventListener;
    @Mock
    private ObjectMapper objectMapper;
    @Mock
    private List<EventHandler<AchievementEventDto>> handlers;
    private AchievementEventDto achievementEventDto;
    private Message message;

    @BeforeEach
    void setUp() throws IOException {
        achievementEventDto = mock(AchievementEventDto.class);

        message = mock(Message.class);
        byte[] body = new byte[0];

        when(message.getBody()).thenReturn(body);
        when(objectMapper.readValue(message.getBody(), AchievementEventDto.class))
                .thenReturn(achievementEventDto);
    }

    @Test
    void onMessage_shouldInvokeObjectMapperReadValueMethod() throws IOException {
        achievementEventListener.onMessage(message, new byte[0]);
        verify(objectMapper).readValue(message.getBody(), AchievementEventDto.class);
    }

    @Test
    void onMessage_shouldInvokeHandleEventMethod() {
        achievementEventListener.onMessage(message, new byte[0]);
        handlers.forEach(handler -> verify(handler).handle(achievementEventDto));
    }
}