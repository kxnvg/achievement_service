package faang.school.achievement.handler;

import faang.school.achievement.dto.RecommendationEventDto;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.model.AchievementProgress;
import faang.school.achievement.model.Rarity;
import faang.school.achievement.service.AchievementProgressService;
import faang.school.achievement.service.AchievementService;
import faang.school.achievement.service.UserAchievementService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class NiceGuyAchievementHandlerTest {
    @Mock
    private AchievementService achievementService;
    @Mock
    private UserAchievementService userAchievementService;
    @Mock
    private AchievementProgressService achievementProgressService;

    private final String titleAchievement = "Просто душка";
    private final String descriptionAchievement = "Получить 10 рекомендаций от других пользователей";
    private final long pointsAchievement = 10;

    private Achievement achievement;
    private RecommendationEventDto recommendationEventDto;
    private AchievementProgress achievementProgress;
    @InjectMocks
    private NiceGuyAchievementHandler niceGuyAchievementHandler =
            new NiceGuyAchievementHandler(achievementService, userAchievementService, achievementProgressService,
                    titleAchievement, descriptionAchievement, pointsAchievement);

    @BeforeEach
    public void init() {
        achievement = Achievement.builder()
                .id(1L)
                .title(titleAchievement)
                .description(descriptionAchievement)
                .rarity(Rarity.RARE)
                .points(pointsAchievement)
                .build();

        recommendationEventDto = RecommendationEventDto.builder()
                .authorId(1)
                .receiverId(2)
                .content("Something nice")
                .build();

        achievementProgress = AchievementProgress.builder()
                .id(1L)
                .achievement(achievement)
                .userId(recommendationEventDto.getAuthorId())
                .currentPoints(9)
                .version(1L)
                .build();
    }

    @Test
    public void testHandler() {
        Mockito.when(achievementService.getByTitle(titleAchievement)).thenReturn(Optional.of(achievement));

        Mockito.when(userAchievementService.hasAchievement(recommendationEventDto.getAuthorId(), achievement.getId())).thenReturn(false);

        Mockito.when(achievementProgressService.getProgress(recommendationEventDto.getAuthorId(), achievement.getId()))
                .thenReturn(Optional.of(achievementProgress));

        achievementProgress.increment();

        Mockito.when(achievementProgressService.updateProgress(achievementProgress)).thenReturn(achievementProgress);
        Mockito.when(userAchievementService.hasAchievement(recommendationEventDto.getAuthorId(), achievement.getId())).thenReturn(false);

        niceGuyAchievementHandler.handle(recommendationEventDto);

        Mockito.verify(userAchievementService, Mockito.times(1))
                .hasAchievement(recommendationEventDto.getAuthorId(), achievement.getId());
    }

    @Test
    public void testHandlerToCreateAchievement() {
        var achievement1 = Achievement.builder()
                .id(0L)
                .title(titleAchievement)
                .description(descriptionAchievement)
                .rarity(null)
                .points(pointsAchievement)
                .build();

        var achievementProgress1 = AchievementProgress.builder()
                .id(0L)
                .achievement(achievement)
                .userId(recommendationEventDto.getAuthorId())
                .currentPoints(0)
                .version(1L)
                .build();

        Mockito.when(achievementService.getByTitle(titleAchievement)).thenReturn(Optional.empty());
        Mockito.when(achievementService.createAchievement(achievement1)).thenReturn(achievement);
        Mockito.when(userAchievementService.hasAchievement(achievement.getId(), recommendationEventDto.getAuthorId())).thenReturn(false);
        Mockito.when(achievementProgressService.getProgress(recommendationEventDto.getAuthorId(), achievement.getId()))
                .thenReturn(Optional.empty());
        Mockito.when(achievementProgressService.createProgressIfNecessary(achievementProgress1)).thenReturn(achievementProgress);

        achievementProgress.increment();

        Mockito.when(achievementProgressService.updateProgress(achievementProgress)).thenReturn(achievementProgress);
        Mockito.when(userAchievementService.hasAchievement(achievement.getId(), recommendationEventDto.getAuthorId())).thenReturn(false);

        niceGuyAchievementHandler.handle(recommendationEventDto);

        Mockito.verify(userAchievementService, Mockito.times(1))
                .hasAchievement(achievement.getId(), recommendationEventDto.getAuthorId());
    }
}