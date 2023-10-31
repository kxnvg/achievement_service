package faang.school.achievement.config;


import faang.school.achievement.listener.CommentEventListener;
import faang.school.achievement.listener.PostEventListener;
import faang.school.achievement.listener.RecommendationEventListener;
import faang.school.achievement.listener.InviteEventListener;
import faang.school.achievement.listener.SkillEventListener;
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

    @Value("${spring.data.redis.channels.recommendation}")
    private String recommendationChannel;

    @Value("${spring.data.redis.channels.invite}")
    private String inviteEventChannelName;

    @Value("${spring.data.redis.channels.post_achievement}")
    private String postEventChannel;

    @Value("${spring.data.redis.channels.comment}")
    private String commentEventChannel;

    @Value("${spring.data.redis.channels.skill}")
    private String skillChannelName;

    @Bean
    public JedisConnectionFactory redisConnectionFactory() {
        RedisStandaloneConfiguration config = new RedisStandaloneConfiguration(host, port);
        return new JedisConnectionFactory(config);
    }

    @Bean
    public MessageListenerAdapter recommendationEventAdapter(RecommendationEventListener recommendationEventListener) {
        return new MessageListenerAdapter(recommendationEventListener, "onMessage");
    }

    @Bean
    public MessageListenerAdapter inviteEventAdapter(InviteEventListener inviteEventListener) {
        return new MessageListenerAdapter(inviteEventListener, "onMessage");
    }

    @Bean
    public MessageListenerAdapter postEventAdapter(PostEventListener postEventListener) {
        return new MessageListenerAdapter(postEventListener, "onMessage");
    }

    @Bean
    public MessageListenerAdapter commentEventAdapter(CommentEventListener commentEventListener) {
        return new MessageListenerAdapter(commentEventListener, "onMessage");
    }
    @Bean
    public MessageListenerAdapter skillAcquiredEventAdapter (SkillEventListener skillEventListener) {
        return new MessageListenerAdapter(skillEventListener, "onMessage");
    }

    @Bean
    RedisMessageListenerContainer redisContainer(MessageListenerAdapter recommendationEventAdapter,
                                                 MessageListenerAdapter inviteEventAdapter,
                                                 MessageListenerAdapter skillAcquiredEventAdapter) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(redisConnectionFactory());
        container.addMessageListener(recommendationEventAdapter, topicRecommendation());
        container.addMessageListener(inviteEventAdapter, topicInviteEvent());
        container.addMessageListener(postEventAdapter, topicPostEvent());
        container.addMessageListener(commentEventAdapter, topicCommentEvent());
        container.addMessageListener(skillAcquiredEventAdapter, topicSkill());
        return container;
    }

    @Bean
    ChannelTopic topicRecommendation() {
        return new ChannelTopic(recommendationChannel);
    }

    @Bean
    ChannelTopic topicPostEvent() {
        return new ChannelTopic(postEventChannel);
    }

    @Bean
    ChannelTopic topicInviteEvent() {
        return new ChannelTopic(inviteEventChannelName);
    }

    @Bean
    ChannelTopic topicCommentEvent() {
        return new ChannelTopic(commentEventChannel);
    }

    @Bean
    ChannelTopic topicSkill(){ return new ChannelTopic(skillChannelName);}
}