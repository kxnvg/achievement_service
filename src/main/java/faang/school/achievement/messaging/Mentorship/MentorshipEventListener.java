package faang.school.achievement.messaging.Mentorship;

import faang.school.achievement.dto.follow.Mentorship.MentorshipEventDto;
import faang.school.achievement.service.handler.EventHandler;
import faang.school.achievement.util.JsonMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
@Slf4j
@AllArgsConstructor
public class MentorshipEventListener implements MessageListener {
    private final JsonMapper jsonMapper;
    private final List<EventHandler<MentorshipEventDto>> eventDtoEventHandler;

    @Override
    public void onMessage(Message message, byte[] pattern) {
        jsonMapper.toObject(message.toString(), MentorshipEventDto.class)
                .ifPresent(mentorshipEventDto -> eventDtoEventHandler
                        .forEach(mentorship -> mentorship.handle(mentorshipEventDto)));
        log.info("MentorshipEventListener has received a message: " + message);
    }
}
