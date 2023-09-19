package faang.school.achievement.messaging.publisher;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import faang.school.achievement.dto.UserAchievementDto;
import faang.school.achievement.mapper.UserAchievementMapper;
import faang.school.achievement.model.UserAchievement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class AchievementEventPublisher {
    private final RedisTemplate<String, Object> redisTemplate;
    private final UserAchievementMapper userAchievementMapper;
    private final ObjectMapper objectMapper;

    @Value("${spring.data.redis.channel.achievement}")
    private String achievementChannelName;


    public void publish(UserAchievement userAchievement) {
        UserAchievementDto message = userAchievementMapper.toDto(userAchievement);
        redisTemplate.convertAndSend(achievementChannelName, message);
        try {
            String json = objectMapper.writeValueAsString(message);
            log.info("A message has been sent to channel {}, message: {}", achievementChannelName, json);
        } catch (JsonProcessingException e) {
            log.error("An exception was thrown when reading a message in AchievementEventPublisher: {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
