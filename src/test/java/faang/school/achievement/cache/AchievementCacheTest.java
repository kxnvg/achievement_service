package faang.school.achievement.cache;

import faang.school.achievement.model.Achievement;
import faang.school.achievement.model.Rarity;
import faang.school.achievement.repository.AchievementRepository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
@Testcontainers
public class AchievementCacheTest {
    @Container
    public static PostgreSQLContainer<?> postgreSQLContainer = new
            PostgreSQLContainer<>(DockerImageName.parse("postgres:latest"))
            .withDatabaseName("mydb")
            .withUsername("myuser")
            .withPassword("mypassword")
            .withInitScript("init.sql");
    @Autowired
    private AchievementRepository achievementRepository;
    @Autowired
    private AchievementCache achievementCache;
    private Achievement achievement;

    @BeforeEach
    void setUp() {
        achievementRepository.deleteAll();
        achievement = Achievement.builder()
                .title("Test Achievement").description("test").points(1).rarity(Rarity.COMMON).build();
        achievementRepository.save(achievement);
    }

    @Test
    public void testGetExistingAchievement() {
        Achievement cachedAchievement = achievementCache.get("Test Achievement");

        assertNotNull(cachedAchievement);
        assertEquals(achievement.getTitle(), cachedAchievement.getTitle());
    }

    @Test
    public void testGetNonExistingAchievement() {
        Achievement cachedAchievement = achievementCache.get("Non Existing Title");

        assertNull(cachedAchievement);
    }
}