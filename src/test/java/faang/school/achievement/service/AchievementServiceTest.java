package faang.school.achievement.service;

import faang.school.achievement.dto.AchievementEventDto;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.model.AchievementProgress;
import faang.school.achievement.model.UserAchievement;
import faang.school.achievement.publisher.AchievementEventPublisher;
import faang.school.achievement.repository.AchievementProgressRepository;
import faang.school.achievement.repository.UserAchievementRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AchievementServiceTest {

    @Mock
    private AchievementProgressRepository achievementProgressRepository;
    @Mock
    private UserAchievementRepository userAchievementRepository;
    @Mock
    private AchievementEventPublisher achievementEventPublisher;

    @InjectMocks
    private AchievementService achievementService;

    private Achievement achievement;

    @BeforeEach
    void setUp() {
        UserAchievement firstUserAchievement = UserAchievement.builder()
                .userId(1)
                .build();
        UserAchievement secondUserAchievement = UserAchievement.builder()
                .userId(2)
                .build();
        achievement = Achievement.builder()
                .id(1)
                .userAchievements(List.of(firstUserAchievement, secondUserAchievement))
                .build();
    }

    @Test
    void giveAchievementTest() {
        achievementService.giveAchievement(achievement, 1);

        verify(userAchievementRepository).save(any(UserAchievement.class));
        verify(achievementEventPublisher).publishMessage(any(AchievementEventDto.class));
    }

    @Test
    void addAchievementToUserIfEnoughPointsTest() {
        AchievementProgress achievementProgress = AchievementProgress.builder()
                .currentPoints(100)
                .build();

        achievementService.addAchievementToUserIfEnoughPoints(achievementProgress, achievement, 1);

        verify(userAchievementRepository).save(any(UserAchievement.class));
        verify(achievementEventPublisher).publishMessage(any(AchievementEventDto.class));
    }

    @Test
    void getUserProgressByAchievementAndUserIdFirstScenarioTest() {
        AchievementProgress expectedAchievementProgress = AchievementProgress.builder()
                .currentPoints(35)
                .build();

        when(achievementProgressRepository.findByUserIdAndAchievementId(1, 1))
                .thenReturn(Optional.of(expectedAchievementProgress));

        AchievementProgress result = achievementService.getUserProgressByAchievementAndUserId(achievement, 1);

        assertEquals(expectedAchievementProgress, result);
    }

    @Test
    void getUserProgressByAchievementAndUserIdSecondScenarioTest() {
        AchievementProgress achievementProgress = AchievementProgress.builder()
                .currentPoints(0)
                .build();

        doReturn(Optional.empty()).doReturn(Optional.of(achievementProgress))
                .when(achievementProgressRepository).findByUserIdAndAchievementId(2, 1);

        AchievementProgress result = achievementService.getUserProgressByAchievementAndUserId(achievement, 2);

        assertEquals(achievementProgress, result);

        verify(achievementProgressRepository).createProgressIfNecessary(2, 1);
        verify(achievementProgressRepository, times(2)).findByUserIdAndAchievementId(2, 1);
    }

    @Test
    void hasAchievementTest() {
        boolean result = achievementService.hasAchievement(achievement, 2);

        assertTrue(result);
    }
}
