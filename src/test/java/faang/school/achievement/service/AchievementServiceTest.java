package faang.school.achievement.service;

import faang.school.achievement.exception.EntityNotFoundException;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.model.AchievementProgress;
import faang.school.achievement.model.Rarity;
import faang.school.achievement.model.UserAchievement;
import faang.school.achievement.repository.AchievementProgressRepository;
import faang.school.achievement.repository.UserAchievementRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class AchievementServiceTest {
    @InjectMocks
    private AchievementService achievementService;
    @Mock
    private UserAchievementRepository userAchievementRepository;
    @Mock
    private AchievementProgressRepository achievementProgressRepository;
    private Achievement achievement;
    private AchievementProgress progress;

    @BeforeEach
    void setUp() {
        achievement = Achievement.builder()
                .id(1L)
                .title("Achievement")
                .description("Description")
                .points(3)
                .rarity(Rarity.COMMON)
                .userAchievements(new ArrayList<>())
                .build();

        progress = AchievementProgress.builder()
                .id(1L)
                .achievement(achievement)
                .userId(1L)
                .currentPoints(1)
                .version(1)
                .build();

        when(userAchievementRepository.existsByUserIdAndAchievementId(1L, 1L))
                .thenReturn(true);
        when(achievementProgressRepository.findByUserIdAndAchievementId(1L, 1L))
                .thenReturn(Optional.ofNullable(progress));
        when(achievementProgressRepository.save(progress))
                .thenReturn(progress);
    }

    @Test
    void hasAchievement_shouldInvokeRepositoryExistsByUserIdAndAchievementIdMethod() {
        achievementService.userHasAchievement(1L, 1L);
        verify(userAchievementRepository).existsByUserIdAndAchievementId(1L, 1L);
    }

    @Test
    void getProgress_shouldInvokeRepositoryFindByUserIdAndAchievementIdMethod() {
        achievementService.getProgress(1L, 1L);
        verify(achievementProgressRepository).findByUserIdAndAchievementId(1L, 1L);
    }

    @Test
    void getProgress_shouldThrowEntityNotFoundException() {
        assertThrows(EntityNotFoundException.class,
                () -> achievementService.getProgress(2L, 1L),
                "Achievement progress with userId: 2 and achievementId: 1 not found");
    }

    @Test
    void createProgressIfNecessary_shouldInvokeRepositoryCreateProgressIfNecessaryMethod() {
        achievementService.createProgressIfNecessary(1L, 1L);
        verify(achievementProgressRepository).createProgressIfNecessary(1L, 1L);
    }

    @Test
    void incrementProgress_shouldIncrementProgress() {
        assertEquals(1, progress.getCurrentPoints());
        achievementService.incrementProgress(progress);
        assertEquals(2, progress.getCurrentPoints());
    }

    @Test
    void incrementProgress_shouldInvokeRepositorySaveMethod() {
        achievementService.incrementProgress(progress);
        verify(achievementProgressRepository).save(progress);
    }

    @Test
    void giveAchievement_shouldInvokeRepositorySaveMethod() {
        achievementService.giveAchievement(1L, achievement);
        verify(userAchievementRepository).save(any(UserAchievement.class));
    }
}