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
import static org.junit.Assert.*;

@SpringBootTest
@ContextConfiguration(classes = TestContainerConfig.class)
public class AchievementCacheTest {
    @Autowired
    private AchievementRepository achievementRepository;
    @Autowired
    private AchievementCache achievementCache;
    private Achievement expectedAchievement;

    @BeforeEach
    void setUp() {
        achievementRepository.deleteAll();
        expectedAchievement = Achievement.builder()
                .title("test").description("test").points(1).rarity(Rarity.COMMON).build();
        achievementRepository.save(expectedAchievement);
    }

    @Test
    public void testGetExistingAchievement() {
        Achievement cachedAchievement = achievementCache.getAchievement("test");

        assertNotNull(cachedAchievement);
        assertEquals(expectedAchievement.getTitle(), cachedAchievement.getTitle());
    }

    @Test
    public void testGetNonExistingAchievement() {
        Achievement cachedAchievement = achievementCache.getAchievement("non existing achievement");

        assertNull(cachedAchievement);
    }
}