package faang.school.achievement.messaging.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import faang.school.achievement.dto.MentorshipStartEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

@Component
public class MentorshipStartListener extends AbstractEventListener<MentorshipStartEvent>
        implements MessageListener {
    @Autowired
    public MentorshipStartListener(ObjectMapper objectMapper) {
        super(objectMapper);
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        message.getBody();
    }
}
