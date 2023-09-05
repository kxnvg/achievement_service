package faang.school.achievement.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import faang.school.achievement.dto.SkillAcquiredEventDto;
import faang.school.achievement.handler.EventHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.data.redis.connection.Message;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class SkillEventListenerTest {
    @InjectMocks
    private SkillEventListener skillEventListener;
    @Mock
    private ObjectMapper objectMapper;
    @Mock
    private List<EventHandler<SkillAcquiredEventDto>> handlers;
    private SkillAcquiredEventDto skillAcquiredEventDto;
    private Message message;

    @BeforeEach
    void setUp() throws IOException {
        skillAcquiredEventDto = mock(SkillAcquiredEventDto.class);

        message = mock(Message.class);
        byte[] body = new byte[0];

        when(message.getBody()).thenReturn(body);
        when(objectMapper.readValue(message.getBody(), SkillAcquiredEventDto.class))
                .thenReturn(skillAcquiredEventDto);
    }

    @Test
    void onMessage_shouldInvokeObjectMapperReadValueMethod() throws IOException {
        skillEventListener.onMessage(message, new byte[0]);
        verify(objectMapper).readValue(message.getBody(), SkillAcquiredEventDto.class);
    }

    @Test
    void onMessage_shouldInvokeHandleEventMethod() {
        skillEventListener.onMessage(message, new byte[0]);
        handlers.forEach(handler -> verify(handler).handle(skillAcquiredEventDto));
    }
}