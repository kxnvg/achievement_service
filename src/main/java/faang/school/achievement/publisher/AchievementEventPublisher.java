package faang.school.achievement.publisher;

import com.fasterxml.jackson.databind.ObjectMapper;
import faang.school.achievement.dto.UserAchievementEventDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Component;

@Component
public class AchievementEventPublisher extends AbstractEventPublisher<UserAchievementEventDto> {
    private final ChannelTopic achievementTopic;

    @Autowired
    public AchievementEventPublisher(RedisTemplate<String,
                                    Object> redisTemplate,
                                     ObjectMapper objectMapper,
                                     ChannelTopic achievementTopic) {
        super(redisTemplate, objectMapper);
        this.achievementTopic = achievementTopic;
    }

    public void publish(UserAchievementEventDto achievement) {
        publishInTopic(achievementTopic, achievement);
    }
}
