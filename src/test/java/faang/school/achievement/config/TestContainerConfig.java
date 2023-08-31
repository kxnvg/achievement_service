package faang.school.achievement.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

@TestConfiguration
public class TestContainerConfig {
    @Bean
    public static PostgreSQLContainer<?> postgreSQLContainer() {
        return new PostgreSQLContainer<>(DockerImageName.parse("postgres:latest"))
                .withDatabaseName("testdb")
                .withUsername("testuser")
                .withPassword("testpassword")
                .withInitScript("init.sql")
                .withReuse(true);
    }
}
