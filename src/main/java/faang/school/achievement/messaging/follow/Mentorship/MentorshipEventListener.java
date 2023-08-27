package faang.school.achievement.messaging.follow.Mentorship;

import com.fasterxml.jackson.databind.ObjectMapper;
import faang.school.achievement.dto.follow.Mentorship.MentorshipEventDto;
import faang.school.achievement.service.handler.EventHandler;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j
@AllArgsConstructor
public class MentorshipEventListener implements MessageListener {
    private final ObjectMapper objectMapper;
    private final EventHandler<MentorshipEventDto> eventDtoEventHandler;

    @Override
    public void onMessage(Message message, byte[] pattern) {
        MentorshipEventDto event = null;
        try {
            event = objectMapper.readValue(message.getBody(), MentorshipEventDto.class);
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
        eventDtoEventHandler.handle(event);
    }
}
