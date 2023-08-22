package faang.school.achievement.config.threadpool;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
@RequiredArgsConstructor
public class AsyncConfig {

    private final WriterAchievementThreadPoolConfig writerAchievementThreadPoolConfig;

    @Bean
    public Executor writerAchievementExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();

        executor.setQueueCapacity(writerAchievementThreadPoolConfig.getQueueCapacity());
        executor.setMaxPoolSize(writerAchievementThreadPoolConfig.getMaxPoolSize());
        executor.setCorePoolSize(writerAchievementThreadPoolConfig.getCorePoolSize());
        executor.initialize();

        return executor;
    }
}
