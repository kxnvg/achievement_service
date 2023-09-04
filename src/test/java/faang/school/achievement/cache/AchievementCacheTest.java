package faang.school.achievement.cache;

import faang.school.achievement.model.Achievement;
import faang.school.achievement.model.Rarity;
import faang.school.achievement.repository.AchievementRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class AchievementCacheTest {
    @Mock
    private AchievementRepository achievementRepository;
    @InjectMocks
    private AchievementCache achievementCache;
    private Achievement achievement1;
    private Achievement achievement2;
    private Achievement achievement3;

    @BeforeEach
    void setUp() {
        achievement1 = new Achievement(1L, "Организатор1", "Организатор1", Rarity.EPIC,
                null, null, 100, LocalDateTime.now(), LocalDateTime.now());
        achievement2 = new Achievement(2L, "Организатор2", "Организатор2", Rarity.COMMON,
                null, null, 10, LocalDateTime.now(), LocalDateTime.now());
        achievement3 = new Achievement(3L, "Организатор3", "Организатор3", Rarity.RARE,
                null, null, 1, LocalDateTime.now(), LocalDateTime.now());
    }

    @Test
    void getAchievement() {
        Mockito.when(achievementRepository.findAll()).thenReturn(List.of(achievement1, achievement2, achievement3));

        achievementCache.init();
        Achievement actual1 = achievementCache.getAchievement("Организатор1");
        Achievement actual2 = achievementCache.getAchievement("Организатор2");
        Achievement actual3 = achievementCache.getAchievement("Организатор3");

        assertEquals(achievement1, actual1);
        assertEquals(achievement2, actual2);
        assertEquals(achievement3, actual3);
    }
}