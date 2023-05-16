package faang.school.achievement.messaging;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import faang.school.achievement.dto.event.AchievementEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AchievementPublisher {

    private final RedisTemplate<String, Object> redisTemplate;
    private final ChannelTopic achievementTopic;
    private final ObjectMapper objectMapper;

    public void publish(AchievementEvent event) {
        try {
            redisTemplate.convertAndSend(achievementTopic.getTopic(), objectMapper.writeValueAsString(event));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
