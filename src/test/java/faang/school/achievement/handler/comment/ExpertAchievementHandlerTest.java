package faang.school.achievement.handler.comment;

import faang.school.achievement.cache.AchievementCache;
import faang.school.achievement.dto.CommentEventDto;
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
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class ExpertAchievementHandlerTest {
    @InjectMocks
    private ExpertAchievementHandler expertAchievementHandler;
    @Mock
    private AchievementCache achievementCache;
    @Mock
    private AchievementService achievementService;
    private Achievement achievement;
    private AchievementProgress progress;
    private CommentEventDto commentEventDto;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(expertAchievementHandler, "achievementTitle", "Expert");

        commentEventDto = CommentEventDto.builder()
                .authorId(1L)
                .build();

        achievement = Achievement.builder()
                .id(1L)
                .title("Expert")
                .description("Description")
                .rarity(Rarity.UNCOMMON)
                .points(3)
                .build();

        progress = AchievementProgress.builder()
                .userId(1L)
                .achievement(achievement)
                .currentPoints(2)
                .build();

        when(achievementCache.get("Expert")).thenReturn(achievement);
        when(achievementService.userHasAchievement(1L, 1L)).thenReturn(false);
        when(achievementService.getProgress(1L, 1L)).thenReturn(progress);
    }

    @Test
    void getAchievementTitleShouldReturnExpert() {
        assertEquals("Expert", expertAchievementHandler.getAchievementTitle());
    }

    @Test
    void shouldInvokeAchievementCacheGetMethod() {
        expertAchievementHandler.handle(commentEventDto);
        verify(achievementCache).get("Expert");
    }

    @Test
    void shouldInvokeAchievementServiceCreateProgressIfNecessaryMethod() {
        expertAchievementHandler.handle(commentEventDto);
        verify(achievementService).createProgressIfNecessary(1L, 1L);
    }

    @Test
    void shouldInvokeAchievementServiceIncrementProgressMethod() {
        expertAchievementHandler.handle(commentEventDto);
        verify(achievementService).incrementProgress(progress);
    }

    @Test
    void shouldInvokeAchievementServiceGiveAchievementMethod() {
        progress.setCurrentPoints(3);

        expertAchievementHandler.handle(commentEventDto);
        verify(achievementService).giveAchievement(1L, achievement);
    }

}