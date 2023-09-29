package faang.school.achievement.handler.skill;

import faang.school.achievement.cache.AchievementCache;
import faang.school.achievement.dto.SkillAcquiredEventDto;
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
class SkillAcquiredHandlerTest {
    @InjectMocks
    private SkillAcquiredHandler skillAcquiredHandler;
    @Mock
    private AchievementCache achievementCache;
    @Mock
    private AchievementService achievementService;
    private Achievement achievement;
    private AchievementProgress progress;
    private SkillAcquiredEventDto skillAcquiredEventDto;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(skillAcquiredHandler, "achievementTitle", "Skill-Master");

        skillAcquiredEventDto = SkillAcquiredEventDto.builder()
                .skillId(1L)
                .receiverId(1L)
                .build();

        achievement = Achievement.builder()
                .id(1L)
                .title("Skill-Master")
                .description("Description")
                .rarity(Rarity.UNCOMMON)
                .points(3)
                .build();

        progress = AchievementProgress.builder()
                .userId(1L)
                .achievement(achievement)
                .currentPoints(2)
                .build();

        when(achievementCache.get("Skill-Master")).thenReturn(achievement);
        when(achievementService.userHasAchievement(1L, 1L)).thenReturn(false);
        when(achievementService.getProgress(1L, 1L)).thenReturn(progress);
    }

    @Test
    void getAchievementTitle_shouldReturnSkillMaster() {
        assertEquals("Skill-Master", skillAcquiredHandler.getAchievementTitle());
    }

    @Test
    void handle_shouldInvokeAchievementCacheGetMethod() {
        skillAcquiredHandler.handle(skillAcquiredEventDto);
        verify(achievementCache).get("Skill-Master");
    }

    @Test
    void handle_shouldStopExecuting() {
        when(achievementService.userHasAchievement(1L, 1L)).thenReturn(true);

        skillAcquiredHandler.handle(skillAcquiredEventDto);
        verify(achievementService).userHasAchievement(1L, 1L);
        verifyNoMoreInteractions(achievementService);
    }

    @Test
    void handle_shouldInvokeAchievementServiceCreateProgressIfNecessaryMethod() {
        skillAcquiredHandler.handle(skillAcquiredEventDto);
        verify(achievementService).createProgressIfNecessary(1L, 1L);
    }

    @Test
    void handle_shouldInvokeAchievementServiceGetProgressMethod() {
        skillAcquiredHandler.handle(skillAcquiredEventDto);
        verify(achievementService).getProgress(1L, 1L);
    }

    @Test
    void handle_shouldInvokeAchievementServiceIncrementProgressMethod() {
        skillAcquiredHandler.handle(skillAcquiredEventDto);
        verify(achievementService).incrementProgress(progress);
    }

    @Test
    void handle_shouldInvokeAchievementServiceGiveAchievementMethod() {
        progress.setCurrentPoints(3);

        skillAcquiredHandler.handle(skillAcquiredEventDto);
        verify(achievementService).giveAchievement(1L, achievement);
    }
}