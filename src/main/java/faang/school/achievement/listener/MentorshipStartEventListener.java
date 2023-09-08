package faang.school.achievement.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import faang.school.achievement.dto.MentorshipStartEventDto;
import faang.school.achievement.handler.EventHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.connection.Message;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
@Slf4j
public class MentorshipStartEventListener extends AbstractListener<MentorshipStartEventDto> {
    @Value("${spring.achievements.mentorship.title}")
    private String mentorshipChannel;

    public MentorshipStartEventListener(ObjectMapper objectMapper, List<EventHandler<MentorshipStartEventDto>> eventHandlers) {
        super(objectMapper, eventHandlers);
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        MentorshipStartEventDto event = readValue(message.getBody(), MentorshipStartEventDto.class);
        log.info("Mentorship start event: {}", event);
        handleEvent(event, mentorshipChannel);
    }
}