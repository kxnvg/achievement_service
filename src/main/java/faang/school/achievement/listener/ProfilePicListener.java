package faang.school.achievement.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import faang.school.achievement.dto.GoalSetEventDto;
import faang.school.achievement.dto.ProfilePicEventDto;
import faang.school.achievement.handler.EventHandler;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProfilePicListener extends AbstractEventListener<ProfilePicEventDto> implements MessageListener {
    public ProfilePicListener(List<EventHandler<ProfilePicEventDto>> eventHandlers, ObjectMapper objectMapper) {
        super(eventHandlers, objectMapper);
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        handleEvent(getMessageBody(message), ProfilePicEventDto.class);
    }
}