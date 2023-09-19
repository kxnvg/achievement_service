package faang.school.achievement.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import faang.school.achievement.messaging.listener.MentorshipStartListener;
import faang.school.achievement.messaging.listener.CommentEventListener;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
@RequiredArgsConstructor
public class RedisConfig {
    @Value("${spring.data.redis.host}")
    private String host;
    @Value("${spring.data.redis.port}")
    private int port;

    @Value("${spring.data.redis.channel.mentorship}")
    private String mentorshipChannelName;
    @Value("${spring.data.redis.channel.comment_events_channel.name}")
    private String commentChannelName;

    @Bean
    public JedisConnectionFactory jedisConnectionFactory() {
        RedisStandaloneConfiguration config = new RedisStandaloneConfiguration(host, port);
        return new JedisConnectionFactory(config);
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(jedisConnectionFactory());

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer =
                new Jackson2JsonRedisSerializer<>(objectMapper, Object.class);

        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(jackson2JsonRedisSerializer);
        return template;
    }

    @Bean
    public MessageListenerAdapter mentorshipListener(MentorshipStartListener mentorshipStartListener) {
        return new MessageListenerAdapter(mentorshipStartListener);
    }

    @Bean
    public MessageListenerAdapter commentListener(CommentEventListener commentEventListener) {
        return new MessageListenerAdapter(commentEventListener);
    }

    @Bean
    public ChannelTopic mentorshipChannel() {
        return new ChannelTopic(mentorshipChannelName);
    }

    @Bean
    public ChannelTopic commentChannel() {
        return new ChannelTopic(commentChannelName);
    }

    @Bean
    public RedisMessageListenerContainer redisContainer(MessageListenerAdapter mentorshipListener,
                                                        MessageListenerAdapter commentListener) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(jedisConnectionFactory());
        container.addMessageListener(mentorshipListener, mentorshipChannel());
        container.addMessageListener(commentListener, commentChannel());
        return container;
    }
}
