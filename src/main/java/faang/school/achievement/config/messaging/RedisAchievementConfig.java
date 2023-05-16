package faang.school.achievement.config.messaging;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.listener.ChannelTopic;

@Configuration
public class RedisAchievementConfig {

    @Value("${spring.data.redis.channel.achievement}")
    private String channel;

    @Bean
    public ChannelTopic achievementTopic() {
        return new ChannelTopic(channel);
    }
}
