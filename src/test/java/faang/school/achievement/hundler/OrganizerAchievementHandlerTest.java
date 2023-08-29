package faang.school.achievement.hundler;

import faang.school.achievement.dto.InviteSentEventDto;
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
class OrganizerAchievementHandlerTest {
    @Mock
    private AchievementService achievementService;

    @Mock
    private UserAchievementService userAchievementService;

    @Mock
    private AchievementProgressService achievementProgressService;

    private final String titleAchievement = "Организатор";
    private final String descriptionAchievement = "Пригласить в проекты 100 человек";
    private final int pointsAchievement = 100;
    private Achievement achievementSaved;
    private AchievementProgress achievementProgressSaved;
    private Achievement achievementNotSaved;
    private AchievementProgress achievementProgressNotSaved;

    @InjectMocks
    private OrganizerAchievementHandler organizerAchievementHandler =
            new OrganizerAchievementHandler(achievementService, userAchievementService, achievementProgressService,
                    titleAchievement, descriptionAchievement, pointsAchievement);

    private InviteSentEventDto inviteSentEventDto;

    @BeforeEach
    void setUp() {
        inviteSentEventDto = InviteSentEventDto.builder()
                .authorId(1L)
                .invitedId(2L)
                .projectId(1L)
                .build();

        achievementSaved = Achievement.builder()
                .id(1L)
                .title(titleAchievement)
                .description(descriptionAchievement)
                .rarity(Rarity.RARE)
                .points(pointsAchievement)
                .build();

        achievementNotSaved = Achievement.builder()
                .id(0L)
                .title(titleAchievement)
                .description(descriptionAchievement)
                .rarity(Rarity.RARE)
                .points(pointsAchievement)
                .build();

        achievementProgressSaved = AchievementProgress.builder()
                .id(1L)
                .achievement(achievementSaved)
                .userId(inviteSentEventDto.getAuthorId())
                .currentPoints(99)
                .version(1L)
                .build();

        achievementProgressNotSaved = AchievementProgress.builder()
                .id(0L)
                .achievement(achievementSaved)
                .userId(inviteSentEventDto.getAuthorId())
                .currentPoints(0)
                .version(1L)
                .build();
    }

    @Test
    void testHandle() {
        Mockito.when(achievementService.getByTitle(titleAchievement)).thenReturn(Optional.of(achievementSaved));
        Mockito.when(userAchievementService.hasAchievement(achievementSaved.getId(), inviteSentEventDto.getAuthorId()))
                .thenReturn(false);
        Mockito.when(achievementProgressService.getProgress(inviteSentEventDto.getAuthorId(), achievementSaved.getId()))
                .thenReturn(Optional.of(achievementProgressSaved));
        achievementProgressSaved.increment();
        Mockito.when(achievementProgressService.updateProgress(achievementProgressSaved)).thenReturn(achievementProgressSaved);
        Mockito.when(userAchievementService.hasAchievement(achievementSaved.getId(), inviteSentEventDto.getAuthorId()))
                        .thenReturn(false);

        organizerAchievementHandler.handle(inviteSentEventDto);

        Mockito.verify(userAchievementService, Mockito.times(1))
                .hasAchievement(achievementSaved.getId(), inviteSentEventDto.getAuthorId());
    }

    @Test
    void testHandleCreateAchievement() {
        Mockito.when(achievementService.getByTitle(titleAchievement)).thenReturn(Optional.empty());
        Mockito.when(achievementService.createAchievement(achievementNotSaved)).thenReturn(achievementSaved);
        Mockito.when(userAchievementService.hasAchievement(achievementSaved.getId(), inviteSentEventDto.getAuthorId()))
                .thenReturn(false);
        Mockito.when(achievementProgressService.getProgress(inviteSentEventDto.getAuthorId(), achievementSaved.getId()))
                .thenReturn(Optional.empty());
        Mockito.when(achievementProgressService.createProgressIfNecessary(achievementProgressNotSaved))
                .thenReturn(achievementProgressSaved);
        achievementProgressSaved.increment();
        Mockito.when(achievementProgressService.updateProgress(achievementProgressSaved)).thenReturn(achievementProgressSaved);
        Mockito.when(userAchievementService.hasAchievement(achievementSaved.getId(), inviteSentEventDto.getAuthorId()))
                .thenReturn(false);

        organizerAchievementHandler.handle(inviteSentEventDto);

        Mockito.verify(userAchievementService, Mockito.times(1))
                .hasAchievement(achievementSaved.getId(), inviteSentEventDto.getAuthorId());
    }
}