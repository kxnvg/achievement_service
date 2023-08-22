package faang.school.achievement.service;

import faang.school.achievement.model.Achievement;
import faang.school.achievement.model.AchievementProgress;
import faang.school.achievement.repository.AchievementProgressRepository;
import faang.school.achievement.repository.AchievementRepository;
import faang.school.achievement.repository.UserAchievementRepository;
import faang.school.achievement.util.exception.AchievementNotCreatedException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AchievementServiceTest {
    @Mock
    private AchievementRepository achievementRepository;
    @Mock
    private UserAchievementRepository userAchievementRepository;
    @Mock
    private AchievementProgressRepository achievementProgressRepository;
    @InjectMocks
    private AchievementService achievementService;
    private Achievement achievement;
    private AchievementProgress achievementProgress;

    @Test
    void getAchievementByTitle_AchievementNotCreated_ShouldThrowException() {
        Mockito.when(achievementRepository.findByTitle(Mockito.anyString())).thenReturn(Optional.empty());

        AchievementNotCreatedException e = assertThrows(AchievementNotCreatedException.class,
                () -> achievementService.getAchievementByTitle("test"));
        assertEquals("Achievement with title test not found", e.getMessage());
    }

    @Test
    void getAchievementByTitle_AchievementCreated_ShouldNotThrowException() {
        Mockito.when(achievementRepository.findByTitle(Mockito.anyString()))
                .thenReturn(Optional.of(Achievement.builder().id(1L).title("test").build()));

        assertDoesNotThrow(() -> achievementService.getAchievementByTitle("test"));
    }

    @Test
    void AchievementAlreadyExistsTest() {
        achievement = Achievement.builder().id(1).build();
        Mockito.when(userAchievementRepository.existsByUserIdAndAchievementId(Mockito.anyLong(),Mockito.anyLong())).thenReturn(true);
        achievementService.updateAchievementProgress(1, achievement);

        Mockito.verify(achievementProgressRepository, Mockito.times(0)).createProgressIfNecessary(Mockito.anyLong(),Mockito.anyLong());
        Mockito.verify(achievementProgressRepository, Mockito.times(0)).findByUserIdAndAchievementId(Mockito.anyLong(),Mockito.anyLong());
    }

    @Test
    void UpdateProgressTest() {
        achievement = Achievement.builder().id(1).points(15).build();
        achievementProgress = AchievementProgress.builder().currentPoints(10).build();
        Mockito.when(userAchievementRepository.existsByUserIdAndAchievementId(Mockito.anyLong(),Mockito.anyLong())).thenReturn(false);
        Mockito.when(achievementProgressRepository.findByUserIdAndAchievementId(Mockito.anyLong(),Mockito.anyLong())).thenReturn(Optional.ofNullable(achievementProgress));

        achievementService.updateAchievementProgress(1, achievement);

        Mockito.verify(userAchievementRepository, Mockito.times(0)).save(Mockito.any());
    }

    @Test
    void UpdateProgressWithCreatingUserAchievementTest() {
        achievement = Achievement.builder().id(1).points(11).build();
        achievementProgress = AchievementProgress.builder().currentPoints(10).build();
        Mockito.when(userAchievementRepository.existsByUserIdAndAchievementId(Mockito.anyLong(),Mockito.anyLong())).thenReturn(false);
        Mockito.when(achievementProgressRepository.findByUserIdAndAchievementId(Mockito.anyLong(),Mockito.anyLong())).thenReturn(Optional.ofNullable(achievementProgress));

        achievementService.updateAchievementProgress(1, achievement);

        Mockito.verify(userAchievementRepository, Mockito.times(1)).save(Mockito.any());
    }
}