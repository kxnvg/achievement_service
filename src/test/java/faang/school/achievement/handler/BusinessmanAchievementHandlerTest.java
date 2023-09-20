package faang.school.achievement.handler;

import faang.school.achievement.dto.ProjectEventDto;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.model.AchievementProgress;
import faang.school.achievement.model.Rarity;
import faang.school.achievement.publisher.AchievementEventPublisher;
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

@ExtendWith(MockitoExtension.class)
class BusinessmanAchievementHandlerTest {

    @Mock
    private AchievementService achievementService;
    @Mock
    private UserAchievementService userAchievementService;
    @Mock
    private AchievementProgressService achievementProgressService;
    @Mock
    private AchievementEventPublisher achievementEventPublisher;
    @InjectMocks
    private BusinessmanAchievementHandler businessmanAchievementHandler;

    private final String title = "Бизнесмен";
    private Achievement achievement;
    private ProjectEventDto projectEventDto;
    private AchievementProgress achievementProgress;
    private Long userId;
    private Long achievementId;


    @BeforeEach
    void setUp() {
        businessmanAchievementHandler = new BusinessmanAchievementHandler(achievementService,
                achievementProgressService, userAchievementService, title, achievementEventPublisher);

        achievement = Achievement.builder()
                .id(1L)
                .title(title)
                .rarity(Rarity.RARE)
                .points(10)
                .build();

        projectEventDto = ProjectEventDto.builder()
                .name("test")
                .ownerId(1L)
                .projectId(1L)
                .build();

        achievementProgress = AchievementProgress.builder()
                .id(1L)
                .achievement(achievement)
                .userId(projectEventDto.getOwnerId())
                .currentPoints(8)
                .version(1L)
                .build();

        userId = projectEventDto.getOwnerId();
        achievementId = achievement.getId();

    }

    @Test
    void testHandle_UserDoesntHaveAchievement() {
        Mockito.when(achievementService.getAchievementFromCache(title)).thenReturn(achievement);
        Mockito.when(userAchievementService.userHasAchievement(userId, achievementId)).thenReturn(true);

        businessmanAchievementHandler.handle(projectEventDto);

        Mockito.verify(achievementProgressService, Mockito.never()).createProgressIfNotExist(userId, achievementId);
    }

    @Test
    void testHandle_UserDoesntHaveEnoughPoints() {
        Mockito.when(achievementService.getAchievementFromCache(title)).thenReturn(achievement);
        Mockito.when(userAchievementService.userHasAchievement(userId, achievementId)).thenReturn(false);
        Mockito.when(achievementProgressService.getByUserIdAndAchievementId(userId, achievementId)).thenReturn(achievementProgress);
        achievementProgress.increment();

        businessmanAchievementHandler.handle(projectEventDto);

        Mockito.verify(achievementProgressService, Mockito.times(1)).incrementProgress(achievementProgress);
        Mockito.verify(userAchievementService, Mockito.never()).createUserAchievementIfNecessary(userId, achievementId);
    }

    @Test
    void testHandle_UserEarnAchievement() {
        Mockito.when(achievementService.getAchievementFromCache(title)).thenReturn(achievement);
        Mockito.when(userAchievementService.userHasAchievement(userId, achievementId)).thenReturn(false);
        Mockito.when(achievementProgressService.getByUserIdAndAchievementId(userId, achievementId)).thenReturn(achievementProgress);
        achievementProgress.setCurrentPoints(10);

        businessmanAchievementHandler.handle(projectEventDto);

        Mockito.verify(achievementProgressService, Mockito.times(1)).incrementProgress(achievementProgress);
        Mockito.verify(userAchievementService, Mockito.times(1)).createUserAchievementIfNecessary(userId, achievementId);
    }
}