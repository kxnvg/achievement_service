package faang.school.achievement.messaging.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import faang.school.achievement.dto.redis.CommentEvent;
import faang.school.achievement.handler.EventHandler;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CommentEventListener extends AbstractEventListener<CommentEvent> implements MessageListener {

    @Autowired
    public CommentEventListener(ObjectMapper objectMapper, List<EventHandler<CommentEvent>> eventHandlers) {
        super(objectMapper, eventHandlers);
    }

    @Override
    public void onMessage(@NonNull Message message, byte[] pattern) {
        CommentEvent event = mapEvent(message, CommentEvent.class);
        for (EventHandler<CommentEvent> eventHandler : eventHandlers){
            eventHandler.handle(event.getAuthorId());
        }
    }
}
