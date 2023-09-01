package faang.school.achievement.handler;

import faang.school.achievement.dto.RecommendationEventDto;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.model.AchievementProgress;
import faang.school.achievement.model.Rarity;
import faang.school.achievement.service.AchievementProgressService;
import faang.school.achievement.service.AchievementService;
import faang.school.achievement.service.UserAchievementService;
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

    @InjectMocks
    private NiceGuyAchievementHandler niceGuyAchievementHandler =
            new NiceGuyAchievementHandler(achievementService, userAchievementService, achievementProgressService,
                    titleAchievement, descriptionAchievement, pointsAchievement);


    @Test
    public void testHandler() {
        var achievement = Achievement.builder()
                .id(1L)
                .title(titleAchievement)
                .description(descriptionAchievement)
                .rarity(Rarity.RARE)
                .points(pointsAchievement)
                .build();

        var recommendationEventDto = RecommendationEventDto.builder().authorId(1)
                .receiverId(2)
                .content("Something nice")
                .build();

        var achievementProgress = AchievementProgress.builder()
                .id(1L)
                .achievement(achievement)
                .userId(recommendationEventDto.getAuthorId())
                .currentPoints(99)
                .version(1L)
                .build();

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
}