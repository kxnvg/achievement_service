package faang.school.achievement.handler.achievement;

import faang.school.achievement.cache.AchievementCache;
import faang.school.achievement.dto.AchievementEventDto;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.model.AchievementProgress;
import faang.school.achievement.model.Rarity;
import faang.school.achievement.service.AchievementService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class RecursionHandlerTest {
    @InjectMocks
    private RecursionHandler recursionHandler;
    @Mock
    private AchievementCache achievementCache;
    @Mock
    private AchievementService achievementService;
    private Achievement achievement;
    private AchievementProgress progress;
    private AchievementEventDto achievementEventDto;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(recursionHandler, "achievementTitle", "Recursion");

        achievementEventDto = AchievementEventDto.builder()
                .authorId(1L)
                .achievementId(3L)
                .build();

        achievement = Achievement.builder()
                .id(3L)
                .title("Recursion")
                .description("Description")
                .rarity(Rarity.RARE)
                .points(1)
                .build();

        progress = AchievementProgress.builder()
                .userId(1L)
                .achievement(achievement)
                .currentPoints(2)
                .build();

        when(achievementCache.get("Recursion")).thenReturn(achievement);
        when(achievementService.userHasAchievement(1L, 3L)).thenReturn(false);
        when(achievementService.getProgress(1L, 3L)).thenReturn(progress);
    }

    @Test
    void getAchievementTitle_shouldReturnSkillMaster() {
        assertEquals("Recursion", recursionHandler.getAchievementTitle());
    }

    @Test
    void handle_shouldInvokeAchievementCacheGetMethod() {
        recursionHandler.handle(achievementEventDto);
        verify(achievementCache).get("Recursion");
    }

    @Test
    void handle_shouldStopExecuting() {
        when(achievementService.userHasAchievement(1L, 3L)).thenReturn(true);

        recursionHandler.handle(achievementEventDto);
        verify(achievementService).userHasAchievement(1L, 3L);
        verifyNoMoreInteractions(achievementService);
    }

    @Test
    void handle_shouldInvokeAchievementServiceCreateProgressIfNecessaryMethod() {
        recursionHandler.handle(achievementEventDto);
        verify(achievementService).createProgressIfNecessary(1L, 3L);
    }

    @Test
    void handle_shouldInvokeAchievementServiceGetProgressMethod() {
        recursionHandler.handle(achievementEventDto);
        verify(achievementService).getProgress(1L, 3L);
    }

    @Test
    void handle_shouldInvokeAchievementServiceIncrementProgressMethod() {
        recursionHandler.handle(achievementEventDto);
        verify(achievementService).incrementProgress(progress);
    }

    @Test
    void handle_shouldInvokeAchievementServiceGiveAchievementMethod() {
        progress.setCurrentPoints(30);

        recursionHandler.handle(achievementEventDto);
        verify(achievementService).giveAchievement(1L, achievement);
    }
}