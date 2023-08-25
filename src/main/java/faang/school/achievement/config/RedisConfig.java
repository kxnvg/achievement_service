package faang.school.achievement.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import faang.school.achievement.subscriber.InviteEventListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

@Configuration
public class RedisConfig {

    @Value("spring.data.redis.host")
    private String host;
    @Value("spring.data.redis.port")
    private int port;
    @Value("spring.data.redis.channels.invitation_channel.name")
    private String invitationTopicName;

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @Bean
    MessageListenerAdapter messageListener() {
        return new MessageListenerAdapter(new InviteEventListener());
    }

    @Bean
    RedisMessageListenerContainer invitationContainer() {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(invitationConnectionFactory());
        container.addMessageListener(messageListener(), invitationTopic());
        return container;
    }

    @Bean
    public JedisConnectionFactory invitationConnectionFactory() {
        RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration(host, port);
        return new JedisConnectionFactory(configuration);
    }

    @Bean
    public ChannelTopic invitationTopic() {
        return new ChannelTopic(invitationTopicName);
    }
}
