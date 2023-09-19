package faang.school.achievement.handler;

import faang.school.achievement.cache.AchievementCache;
import faang.school.achievement.messaging.publisher.AchievementEventPublisher;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.model.AchievementProgress;
import faang.school.achievement.repository.AchievementProgressRepository;
import faang.school.achievement.service.AchievementService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class AbstractAchievementHandlerTest {
    @Mock
    private AchievementProgressRepository achievementProgressRepository;
    @Mock
    private AchievementService achievementService;
    @Mock
    private AchievementCache achievementCache;
    @Mock
    private AchievementEventPublisher achievementEventPublisher;

    @InjectMocks
    private SenseyAchievementHandler senseyAchievementHandler;

    private Achievement achievement;
    private AchievementProgress achievementProgress;
    private final long userId = 1L;


    @BeforeEach
    public void initAchievementProgress() {
        achievement = Achievement.builder()
                .id(1L)
                .points(30)
                .build();
        achievementProgress = AchievementProgress.builder()
                .id(1L)
                .userId(userId)
                .achievement(achievement)
                .build();
    }

    @Test
    public void shouldSaveUserAchievement() {
        achievementProgress.setCurrentPoints(29);

        initMocks();
        senseyAchievementHandler.handle(userId);

        Mockito.verify(achievementService, Mockito.times(1))
                .saveAchievement(Mockito.any());
    }

    @Test
    public void shouldNotSaveUserAchievement() {
        achievementProgress.setCurrentPoints(0);

        initMocks();
        senseyAchievementHandler.handle(userId);

        Mockito.verify(achievementService, Mockito.times(0))
                .saveAchievement(Mockito.any());
    }

    private void initMocks() {
        Mockito.when(achievementCache.get("Сенсей")).thenReturn(achievement);
        Mockito.when(achievementService.hasAchievement(userId, achievement.getId()))
                .thenReturn(false);
        Mockito.lenient().when(achievementService.getAchievementProgress(userId, achievement.getId()))
                .thenReturn(achievementProgress);
    }
}
