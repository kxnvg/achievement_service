package faang.school.achievement.messaging.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import faang.school.achievement.dto.redis.MentorshipStartEvent;
import faang.school.achievement.handler.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MentorshipStartListener extends AbstractEventListener<MentorshipStartEvent>
        implements MessageListener {
    @Autowired
    public MentorshipStartListener(ObjectMapper objectMapper, List<EventHandler<MentorshipStartEvent>> eventHandlers) {
        super(objectMapper, eventHandlers);
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        MentorshipStartEvent event = mapEvent(message, MentorshipStartEvent.class);
        for (EventHandler<MentorshipStartEvent> eventHandler : eventHandlers) {
            eventHandler.handle(event.getMentorId());
        }
    }
}
