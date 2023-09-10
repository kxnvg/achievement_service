package faang.school.achievement.messaging.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import faang.school.achievement.dto.redis.CommentEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

@Component
public class CommentEventListener extends AbstractEventListener<CommentEvent> implements MessageListener {

    @Autowired
    public CommentEventListener(ObjectMapper objectMapper) {
        super(objectMapper);
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        CommentEvent event = eventMapper(message, CommentEvent.class);
    }
}
