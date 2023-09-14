package faang.school.achievement.listener;

import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

@Component
public class RecommendationListener extends AbstractEventListener implements MessageListener {
    @Override
    public void onMessage(Message message, byte[] pattern) {

    }
}
