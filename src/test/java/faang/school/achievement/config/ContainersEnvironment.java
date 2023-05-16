package faang.school.achievement.config;

import faang.school.achievement.containers.PostgresTestContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
public class ContainersEnvironment {

    @Container
    public static PostgreSQLContainer<?> postgreSQLContainer = PostgresTestContainer.getInstance();
}
