package faang.school.achievement.config.threadpool;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Data
@ConfigurationProperties(prefix = "writer-achievement.thread-pool")
public class WriterAchievementThreadPoolConfig {
    private int queueCapacity;
    private int maxPoolSize;
    private int corePoolSize;
}
