package faang.school.achievement.service;

import faang.school.achievement.cache.AchievementCache;
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

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class AchievementServiceTest {

    @Mock
    private AchievementRepository achievementRepository;
    @Mock
    private AchievementCache achievementCache;
    @InjectMocks
    private AchievementService achievementService;

    private String title;
    private Achievement achievement;

    @BeforeEach
    void setUp() {
        title = "Such a Sweetheart";

        achievement = Achievement.builder()
                .id(1L)
                .title(title)
                .rarity(Rarity.RARE)
                .points(10)
                .build();
    }

    @Test
    void getAchievementByTitle() {
        Mockito.when(achievementRepository.getAchievementByTitle(title)).thenReturn(Optional.of(achievement));

        Achievement achievementByTitle = achievementService.getAchievementByTitle(title);

        Mockito.verify(achievementRepository, Mockito.times(1)).getAchievementByTitle(title);
        assertEquals(achievement, achievementByTitle);
    }

    @Test
    void getAchievementFromCache() {
        Mockito.when(achievementCache.getAchievement(title)).thenReturn(achievement);

        achievementService.getAchievementFromCache(title);

        Mockito.verify(achievementCache, Mockito.times(1)).getAchievement(title);
    }
}