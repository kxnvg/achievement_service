package faang.school.achievement.messaging;

import faang.school.achievement.dto.achievement.AchievementEvent;
import faang.school.achievement.util.JsonMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AchievementPublisher {
    private final RedisTemplate<String, Object> redisTemplate;
    private final ChannelTopic achievementTopic;
    private final JsonMapper jsonMapper;

    public void publish(AchievementEvent event) {
        jsonMapper.toJson(event).ifPresent(
                (eventJson) -> redisTemplate.convertAndSend(achievementTopic.getTopic(), eventJson)
        );
    }
}
