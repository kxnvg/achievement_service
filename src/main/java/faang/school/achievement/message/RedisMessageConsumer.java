package faang.school.achievement.message;

import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class RedisMessageConsumer implements MessageListener {

    public static List<String> achievementList = new ArrayList();

    public void onMessage(Message message, byte[] pattern) {
        achievementList.add(message.toString());
        System.out.println("Message received: " + new String(message.getBody()));
    }
}
