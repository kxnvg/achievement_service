package faang.school.achievement.message.publisher;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import faang.school.achievement.dto.ExpertAchievementDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentEventPublisher implements MessagePublisher<ExpertAchievementDto> {
    @Value("${spring.data.redis.channel.achievement}")
    private String channelTopic;
    private final RedisTemplate<String, Object> redisTemplate;
    private final ObjectMapper objectMapper;
    @Override
    public void publish(ExpertAchievementDto message) {
        String json;
        try {
            json = objectMapper.writeValueAsString(message);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        redisTemplate.convertAndSend(channelTopic, json);
    }
}
