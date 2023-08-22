package faang.school.achievement.cache;

import faang.school.achievement.model.Achievement;
import faang.school.achievement.model.Rarity;
import faang.school.achievement.repository.AchievementRepository;
import faang.school.achievement.testconfig.TestContainerConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
@Testcontainers
@ContextConfiguration(classes = TestContainerConfig.class)
public class AchievementCacheTest {
    @Autowired
    private PostgreSQLContainer<?> postgreSQLContainer;
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