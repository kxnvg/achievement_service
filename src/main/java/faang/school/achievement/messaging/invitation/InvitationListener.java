package faang.school.achievement.messaging.invitation;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class InvitationListener implements MessageListener {
    private final ObjectMapper mapper;
    @Override
    public void onMessage(Message message, byte[] pattern) {

    }
}
