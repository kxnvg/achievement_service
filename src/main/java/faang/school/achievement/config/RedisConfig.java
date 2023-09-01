package faang.school.achievement.config;

import faang.school.achievement.listener.SkillEventListener;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class RedisConfig {
    @Value("${spring.data.redis.host}")
    private String host;
    @Value("${spring.data.redis.port}")
    private int port;
    @Value("${spring.data.redis.channels.skill}")
    private String skillChannel;

    @Bean
    public JedisConnectionFactory jedisConnectionFactory() {
        log.info("Created new redis connection factory with host: {}, port: {}", host, port);
        return new JedisConnectionFactory();
    }

    @Bean
    public MessageListenerAdapter skillAdapter(SkillEventListener skillEventListener) {
        return new MessageListenerAdapter(skillEventListener);
    }

    @Bean
    public ChannelTopic skillTopic() {
        return new ChannelTopic(skillChannel);
    }

    @Bean
    public RedisMessageListenerContainer redisContainer(MessageListenerAdapter skillAdapter) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(jedisConnectionFactory());
        container.addMessageListener(skillAdapter, skillTopic());
        return container;
    }
}
