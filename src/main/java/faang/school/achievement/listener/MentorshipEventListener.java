package faang.school.achievement.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import faang.school.achievement.dto.DtoMentorshipStartEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class MentorshipEventListener implements MessageListener {
    private final ObjectMapper objectMapper;
    private DtoMentorshipStartEvent mentorshipStartEvent;

    @Override
    public void onMessage(Message message, byte[] pattern) {
        try {
            mentorshipStartEvent = objectMapper.readValue(message.getBody(), DtoMentorshipStartEvent.class);
        } catch (IOException ignored) {
        }

    }
}
