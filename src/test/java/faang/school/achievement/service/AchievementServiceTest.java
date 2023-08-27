package faang.school.achievement.service;

import faang.school.achievement.model.Achievement;
import faang.school.achievement.model.AchievementProgress;
import faang.school.achievement.publisher.AchievementPublisher;
import faang.school.achievement.repository.AchievementProgressRepository;
import faang.school.achievement.repository.AchievementRepository;
import faang.school.achievement.repository.UserAchievementRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AchievementServiceTest {

    @Mock
    private AchievementPublisher achievementPublisher;
    @Mock
    private AchievementProgressRepository progressRepository;
    @Mock
    private AchievementRepository achievementRepository;
    @Mock
    private UserAchievementRepository userAchievementRepository;
    @InjectMocks
    private AchievementService achievementService;

    private AchievementProgress achievementProgress;
    private Achievement achievement;
    private final long ACHIEVEMENT_ID = 1L;
    private final long AUTHOR_ID = 1L;
    private final long CURRENT_POINTS = 999L;
    private final String ACHIEVEMENT_TITTLE = "Opinion leader";

    @BeforeEach
    void initData() {
        achievementProgress = AchievementProgress.builder()
                .userId(AUTHOR_ID)
                .currentPoints(CURRENT_POINTS)
                .build();
        achievement = Achievement.builder()
                .id(ACHIEVEMENT_ID)
                .title(ACHIEVEMENT_TITTLE)
                .build();
    }

    @Test
    void testHasAchievement() {
        achievementService.hasAchievement(AUTHOR_ID, ACHIEVEMENT_ID);
        verify(userAchievementRepository).existsByUserIdAndAchievementId(AUTHOR_ID, ACHIEVEMENT_ID);
    }

    @Test
    void testCreateProgressIfNecessary() {
        achievementService.createProgressIfNecessary(ACHIEVEMENT_ID, AUTHOR_ID);
        verify(progressRepository).createProgressIfNecessary(AUTHOR_ID, ACHIEVEMENT_ID);
    }

    @Test
    void testGetProgress() {
        when(progressRepository.findByUserIdAndAchievementId(AUTHOR_ID, ACHIEVEMENT_ID))
                .thenReturn(Optional.ofNullable(achievementProgress));
        long actualProgress = achievementService.getProgress(AUTHOR_ID, ACHIEVEMENT_ID);

        assertEquals(1000L, actualProgress);
        verify(progressRepository).save(achievementProgress);
    }
}
