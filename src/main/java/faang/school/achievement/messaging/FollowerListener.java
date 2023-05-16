package faang.school.achievement.messaging;

import com.fasterxml.jackson.databind.ObjectMapper;
import faang.school.achievement.dto.event.FollowerEvent;
import faang.school.achievement.handler.follower.FollowerEventHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class FollowerListener implements MessageListener {

    private final ObjectMapper objectMapper;
    private final List<FollowerEventHandler> followerEventHandlers;

    @Override
    public void onMessage(Message message, byte[] pattern) {
        try {
            var follower = objectMapper.readValue(message.getBody(), FollowerEvent.class);
            log.info("Received message: {}", follower);
            followerEventHandlers.forEach(handler -> handler.handle(follower));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
