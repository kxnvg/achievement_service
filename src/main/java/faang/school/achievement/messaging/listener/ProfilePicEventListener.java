package faang.school.achievement.messaging.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import faang.school.achievement.dto.redis.ProfilePicEvent;
import faang.school.achievement.handler.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProfilePicEventListener extends AbstractEventListener<ProfilePicEvent> implements MessageListener {

    @Autowired
    public ProfilePicEventListener(ObjectMapper objectMapper, List<EventHandler<ProfilePicEvent>> eventHandlers) {
        super(objectMapper, eventHandlers);
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        ProfilePicEvent event = mapEvent(message, ProfilePicEvent.class);

        for (var handler : eventHandlers) {
            handler.handle(event.getUserId());
        }
    }
}
