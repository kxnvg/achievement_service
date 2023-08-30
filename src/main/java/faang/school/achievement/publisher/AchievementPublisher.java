package faang.school.achievement.publisher;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import faang.school.achievement.dto.AchievementEventDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class AchievementPublisher {

    private final ChannelTopic achievementTopicName;
    @Qualifier("redisPublisherTemplate")
    private final RedisTemplate<String, Object> redisPublisherTemplate;
    private final ObjectMapper objectMapper;

    public void publish(AchievementEventDto achievementEventDto) {
        log.info("AchievementPublisher is sending message to Redis...");
        try {
            String jsonString = objectMapper.writeValueAsString(achievementEventDto);
            redisPublisherTemplate.convertAndSend(achievementTopicName.getTopic(), jsonString);
        } catch (JsonProcessingException e) {
            log.error("JsonProcessingException while parsing message to Redis...");
            throw new RuntimeException(e);
        }
    }
}
