package faang.school.achievement.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import faang.school.achievement.dto.MentorshipStartEventDto;
import faang.school.achievement.handler.EventHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.connection.Message;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class MentorshipStartEventListener extends AbstractListener<MentorshipStartEventDto> {
    @Value("${spring.data.redis.channels.mentorship_channel}")
    private String mentorshipChannel;

    public MentorshipStartEventListener(ObjectMapper objectMapper, List<EventHandler<MentorshipStartEventDto>> eventHandlers) {
        super(objectMapper, eventHandlers);
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        MentorshipStartEventDto event = readValue(message.getBody(), MentorshipStartEventDto.class);
        handleEvent(event, mentorshipChannel);
    }
}