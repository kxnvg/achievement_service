package faang.school.achievement.service;

import faang.school.achievement.messaging.AchievementPublisher;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.model.AchievementProgress;
import faang.school.achievement.repository.AchievementProgressRepository;
import faang.school.achievement.repository.AchievementRepository;
import faang.school.achievement.repository.UserAchievementRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class AchievementServiceTest {
    @Mock
    private AchievementRepository achievementRepository;
    @Mock
    private UserAchievementRepository userAchievementRepository;
    @Mock
    private AchievementProgressRepository achievementProgressRepository;
    @Mock
    private AchievementPublisher achievementPublisher;

    @InjectMocks
    private AchievementService achievementService;
    private Achievement achievement;
    private AchievementProgress achievementProgress;

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
