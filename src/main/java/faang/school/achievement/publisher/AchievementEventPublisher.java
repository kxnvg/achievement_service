package faang.school.achievement.publisher;


import faang.school.achievement.dto.AchievementEventDto;
import faang.school.achievement.listener.JsonObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
@RequiredArgsConstructor
@Setter
@Slf4j
public class AchievementEventPublisher {

    private final RedisTemplate<String, Object> redisTemplate;
    private final JsonObjectMapper mapper;

    @Value("${spring.data.redis.channel.achievement}")
    private String achievementTopicName;

    public void publishMessage(AchievementEventDto event) {
        String json = mapper.writeValueAsString(event);
        redisTemplate.convertAndSend(achievementTopicName, json);

        log.info("Achievement event sending with achievementId: {}, receiverId: {}, has been sent",
                event.getAchievementId(), event.getReceiverId());
    }
}
