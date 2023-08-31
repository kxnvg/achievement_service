package faang.school.achievement.config;

import faang.school.achievement.messaging.follow.FollowEventListener;
import faang.school.achievement.messaging.follow.Mentorship.MentorshipEventListener;
import faang.school.achievement.messaging.invitation.InvitationListener;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
@RequiredArgsConstructor
public class RedisConfig {
    @Value("${spring.data.redis.host}")
    private String host;
    @Value("${spring.data.redis.port}")
    private int port;
    @Value("${spring.data.redis.channels.follower_channel.name}")
    private String followerTopic;
    private final FollowEventListener followEventListener;
    private final MentorshipEventListener mentorshipEventListener;
    @Value("${spring.data.redis.channels.mentorship_event_topic.name}")
    private String mentorshipEventTopic;
    @Value("${spring.data.redis.channels.invitation_channel.name}")
    private String stageInvitationTopic;
    @Value("${spring.data.redis.channels.achievement_topic.name}")
    private String achievementTopic;

    @Bean
    public JedisConnectionFactory redisConnectionFactory() {
        System.out.println(port);
        RedisStandaloneConfiguration config = new RedisStandaloneConfiguration(host, port);
        return new JedisConnectionFactory(config);
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new StringRedisSerializer());

        return redisTemplate;
    }

    @Bean
    ChannelTopic followerTopic() {
        return new ChannelTopic(followerTopic);
    }

    @Bean
    ChannelTopic mentorshipEventTopic() {
        return new ChannelTopic(mentorshipEventTopic);
    }

    @Bean
    ChannelTopic stageInvitationTopic() {
        return new ChannelTopic(stageInvitationTopic);
    }
    @Bean
    ChannelTopic achievementTopic() {
        return new ChannelTopic(achievementTopic);
    }

    @Bean
    MessageListenerAdapter followEventAdapter(FollowEventListener followEventListener) {
        return new MessageListenerAdapter(followEventListener);
    }
    @Bean
    MessageListenerAdapter invitationAdapter(InvitationListener invitationListener) {
        return new MessageListenerAdapter(invitationListener);
    }

    @Bean
    RedisMessageListenerContainer redisContainer(
            MessageListenerAdapter followEventAdapter,
            MessageListenerAdapter invitationAdapter
    ) {
        final RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(redisConnectionFactory());
        container.addMessageListener(followEventAdapter, followerTopic());
        container.addMessageListener(invitationAdapter, stageInvitationTopic());
        container.addMessageListener(new MessageListenerAdapter(followEventListener), followerTopic());
        return container;
    }
}
