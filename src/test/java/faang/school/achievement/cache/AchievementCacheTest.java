package faang.school.achievement.cache;

import faang.school.achievement.config.TestContainerConfig;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.model.Rarity;
import faang.school.achievement.repository.AchievementRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.Assert.*;

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
                .title("test").description("test").points(1).rarity(Rarity.COMMON).build();
        achievementRepository.save(achievement);
    }

    @Test
    public void testGetExistingAchievement() {
        Achievement cachedAchievement = achievementCache.getAchievement("test");

        assertNotNull(cachedAchievement);
        assertEquals(achievement.getTitle(), cachedAchievement.getTitle());
    }

    @Test
    public void testGetNonExistingAchievement() {
        Achievement cachedAchievement = achievementCache.getAchievement("non existing achievement");

        assertNull(cachedAchievement);
    }

}
