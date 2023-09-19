package faang.school.achievement.messaging.handler;

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

@ExtendWith(MockitoExtension.class)
class SuchASweetheartAchievementHandlerTest {

    @Mock
    private AchievementService achievementService;
    @Mock
    private UserAchievementService userAchievementService;
    @Mock
    private AchievementProgressService achievementProgressService;
    @InjectMocks
    private SuchASweetheartAchievementHandler niceGuyAchievementHandler;

    private final String title = "Such a Sweetheart";
    private Achievement achievement;
    private RecommendationEventDto recommendationEventDto;
    private AchievementProgress achievementProgress;
    private Long userId;
    private Long achievementId;


    @BeforeEach
    void setUp() {
        niceGuyAchievementHandler = new SuchASweetheartAchievementHandler(achievementService, achievementProgressService, userAchievementService,
                title);

        achievement = Achievement.builder()
                .id(1L)
                .title(title)
                .rarity(Rarity.RARE)
                .points(10)
                .build();

        recommendationEventDto = RecommendationEventDto.builder()
                .authorId(1L)
                .receiverId(2L)
                .build();

        achievementProgress = AchievementProgress.builder()
                .id(1L)
                .achievement(achievement)
                .userId(recommendationEventDto.getAuthorId())
                .currentPoints(8)
                .version(1L)
                .build();

        userId = recommendationEventDto.getReceiverId();
        achievementId = achievement.getId();

    }

    @Test
    void testHandle_UserDoesntHaveAchievement() {
        Mockito.when(achievementService.getAchievementFromCache(title)).thenReturn(achievement);
        Mockito.when(userAchievementService.userHasAchievement(userId, achievementId)).thenReturn(true);

        niceGuyAchievementHandler.handle(recommendationEventDto);

        Mockito.verify(achievementProgressService, Mockito.never()).createProgressIfNotExist(userId, achievementId);
    }

    @Test
    void testHandle_UserDoesntHaveEnoughPoints() {
        Mockito.when(achievementService.getAchievementFromCache(title)).thenReturn(achievement);
        Mockito.when(userAchievementService.userHasAchievement(userId, achievementId)).thenReturn(false);
        Mockito.when(achievementProgressService.getByUserIdAndAchievementId(userId, achievementId)).thenReturn(achievementProgress);
        achievementProgress.increment();

        niceGuyAchievementHandler.handle(recommendationEventDto);

        Mockito.verify(achievementProgressService, Mockito.times(1)).incrementProgress(achievementProgress);
        Mockito.verify(userAchievementService, Mockito.never()).createUserAchievementIfNecessary(userId, achievementId);
    }

    @Test
    void testHandle_UserEarnAchievement() {
        Mockito.when(achievementService.getAchievementFromCache(title)).thenReturn(achievement);
        Mockito.when(userAchievementService.userHasAchievement(userId, achievementId)).thenReturn(false);
        Mockito.when(achievementProgressService.getByUserIdAndAchievementId(userId, achievementId)).thenReturn(achievementProgress);
        achievementProgress.setCurrentPoints(10);

        niceGuyAchievementHandler.handle(recommendationEventDto);

        Mockito.verify(achievementProgressService, Mockito.times(1)).incrementProgress(achievementProgress);
        Mockito.verify(userAchievementService, Mockito.times(1)).createUserAchievementIfNecessary(userId, achievementId);
    }
}