package faang.school.achievement.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import faang.school.achievement.dto.ProfilePicEventDto;
import faang.school.achievement.handler.EventHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.connection.Message;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProfilePicEventListener extends AbstractListener<ProfilePicEventDto> {

    public ProfilePicEventListener(ObjectMapper objectMapper,
                                   List<EventHandler<ProfilePicEventDto>> eventHandlers,
                                   @Value("${spring.achievements.profile.handsome.title}") String achievementTitle) {
        super(objectMapper, eventHandlers, achievementTitle);
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        ProfilePicEventDto event = readValue(message.getBody(), ProfilePicEventDto.class);
        handleEvent(event);
    }
}
