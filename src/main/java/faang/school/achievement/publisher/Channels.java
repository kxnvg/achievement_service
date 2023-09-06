package faang.school.achievement.publisher;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class Channels {
    @Value("${spring.data.redis.channels.achievement.name}")
    private String userAchievements;
}
