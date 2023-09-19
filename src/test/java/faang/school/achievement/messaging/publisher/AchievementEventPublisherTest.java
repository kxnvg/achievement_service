package faang.school.achievement.messaging.publisher;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import faang.school.achievement.dto.UserAchievementDto;
import faang.school.achievement.mapper.UserAchievementMapper;
import faang.school.achievement.model.UserAchievement;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.core.RedisTemplate;

@ExtendWith(MockitoExtension.class)
public class AchievementEventPublisherTest {
    @Mock
    private RedisTemplate<String, Object> redisTemplate;
    @Mock
    private UserAchievementMapper userAchievementMapper;
    @Mock
    private ObjectMapper objectMapper;
    @InjectMocks
    private AchievementEventPublisher achievementEventPublisher;

    @Test
    public void testPublish() throws JsonProcessingException {
        UserAchievement userAchievement = UserAchievement.builder().build();
        UserAchievementDto messageMock = userAchievementMapper.toDto(userAchievement);
        String json = objectMapper.writeValueAsString(messageMock);

        Mockito.when(userAchievementMapper.toDto(Mockito.any())).thenReturn(messageMock);
        Mockito.when(objectMapper.writeValueAsString(messageMock)).thenReturn(json);

        achievementEventPublisher.publish(userAchievement);

        Mockito.verify(redisTemplate, Mockito.times(1))
                .convertAndSend(Mockito.any(), Mockito.eq(messageMock));
    }
}
