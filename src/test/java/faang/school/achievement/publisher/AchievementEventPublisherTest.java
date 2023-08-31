package faang.school.achievement.publisher;

import faang.school.achievement.dto.AchievementEventDto;
import faang.school.achievement.listener.JsonObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.core.RedisTemplate;

import java.time.LocalDateTime;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

//@ExtendWith(MockitoExtension.class)
//class AchievementEventPublisherTest {
//
//    @Mock
//    private RedisTemplate<String, Object> redisTemplate;
//    @Mock
//    private JsonObjectMapper mapper;
//
//    @InjectMocks
//    private AchievementEventPublisher achievementEventPublisher;
//
//    @Test
//    void publishMessage() {
//        achievementEventPublisher.setAchievementTopicName("achievement_channel");
//
//        LocalDateTime currentTime = LocalDateTime.now();
//
//        AchievementEventDto achievementEventDto = AchievementEventDto.builder()
//                .achievementId(1)
//                .receiverId(1)
//                .achievedAt(currentTime)
//                .build();
//
//        String json = "{\"achievementId\":123,\"receiverId\":456,\"achievedAt\":\"2023-08-20T12:34:56.789\"}";
//
//        when(mapper.writeValueAsString(achievementEventDto)).thenReturn(json);
//
//        achievementEventPublisher.publishMessage(achievementEventDto);
//
//        verify(mapper).writeValueAsString(achievementEventDto);
//        verify(redisTemplate).convertAndSend("achievement_channel", json);
//    }
//
//}