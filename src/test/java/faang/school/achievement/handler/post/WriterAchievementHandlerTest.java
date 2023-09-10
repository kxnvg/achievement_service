package faang.school.achievement.handler.post;

import faang.school.achievement.cache.AchievementCache;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.model.AchievementProgress;
import faang.school.achievement.dto.PostEventDto;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class WriterAchievementHandlerTest {

    @InjectMocks
    private WriterAchievementHandler writerAchievementHandler;
    @Mock
    private AchievementCache achievementCache;
    @Mock
    private AchievementService achievementService;
    private Achievement achievement;
    private AchievementProgress progress;
    private PostEventDto postEventDto;


    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(writerAchievementHandler, "achievementTitle", "Writer");

        postEventDto = PostEventDto.builder()
                .authorId(1L)
                .postId(1L)
                .build();

        achievement = Achievement.builder()
                .id(1L)
                .title("Writer")
                .description("Description")
                .rarity(Rarity.UNCOMMON)
                .points(100)
                .build();

        progress = AchievementProgress.builder()
                .id(1L)
                .achievement(achievement)
                .currentPoints(99)
                .build();

        when(achievementCache.get("Writer")).thenReturn(achievement);
        when(achievementService.userHasAchievement(1L, 1L)).thenReturn(false);
        when(achievementService.getProgress(1L, 1L)).thenReturn(progress);
    }

    @Test
    void getAchievementTitle_shouldReturnAchievementTitle() {
        assertEquals("Writer", writerAchievementHandler.getAchievementTitle());
    }

    @Test
    void handle_shouldInvokeAchievementCacheGetMethod() {
        writerAchievementHandler.handle(postEventDto);
        verify(achievementCache).get("Writer");
    }

    @Test
    void handle_shouldStopExecuting() {
        when(achievementService.userHasAchievement(1L, 1L)).thenReturn(true);

        writerAchievementHandler.handle(postEventDto);
        verify(achievementService).userHasAchievement(1L, 1L);
        verifyNoMoreInteractions(achievementService);
    }

    @Test
    void handle_shouldInvokeAchievementServiceCreateProgressIfNecessaryMethod() {
        writerAchievementHandler.handle(postEventDto);
        verify(achievementService).createProgressIfNecessary(1L, 1L);
    }

    @Test
    void handle_shouldInvokeAchievementServiceGetProgressMethod() {
        writerAchievementHandler.handle(postEventDto);
        verify(achievementService).getProgress(1L, 1L);
    }

    @Test
    void handle_shouldInvokeAchievementServiceIncrementProgressMethod() {
        writerAchievementHandler.handle(postEventDto);
        verify(achievementService).incrementProgress(progress);
    }

    @Test
    void handle_shouldInvokeAchievementServiceGiveAchievementMethod() {
        progress.setCurrentPoints(100);

        writerAchievementHandler.handle(postEventDto);
        verify(achievementService).giveAchievement(1L, achievement);
    }
}