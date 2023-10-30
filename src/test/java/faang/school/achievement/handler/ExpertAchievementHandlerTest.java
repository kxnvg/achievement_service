package faang.school.achievement.handler;

import faang.school.achievement.dto.CommentEventDto;
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
class ExpertAchievementHandlerTest {
    @Mock
    private AchievementService achievementService;
    @Mock
    private UserAchievementService userAchievementService;
    @Mock
    private AchievementProgressService achievementProgressService;

    private final String titleAchievement = "Эксперт";
    private Achievement achievementSaved;
    private AchievementProgress achievementProgressSaved;
    private CommentEventDto commentEventDto;
    @InjectMocks
    private ExpertAchievementHandler expertAchievementHandler =
            new ExpertAchievementHandler(achievementService,
                    userAchievementService,
                    achievementProgressService,
                    titleAchievement);

    @BeforeEach
    void setUp() {
        commentEventDto = CommentEventDto.builder()
                .authorId(1L)
                .postId(1L)
                .content("content")
                .build();

        achievementSaved = Achievement.builder()
                .id(1L)
                .title(titleAchievement)
                .rarity(Rarity.LEGENDARY)
                .build();

        achievementProgressSaved = AchievementProgress.builder()
                .id(1L)
                .achievement(achievementSaved)
                .userId(commentEventDto.getAuthorId())
                .currentPoints(99)
                .build();
    }

    @Test
    void testHandler(){
        Mockito.when(achievementService.getAchievement(titleAchievement))
                .thenReturn(achievementSaved);
        Mockito.when(userAchievementService.hasAchievement(achievementSaved.getId(), commentEventDto.getAuthorId()))
                .thenReturn(false);
        Mockito.when(achievementProgressService.getProgress(commentEventDto.getAuthorId(), achievementSaved.getId()))
                .thenReturn(achievementProgressSaved);
        achievementProgressSaved.increment();
        Mockito.when(achievementProgressService.updateProgress(achievementProgressSaved))
                .thenReturn(achievementProgressSaved);

        expertAchievementHandler.handle(commentEventDto.getAuthorId());

        Mockito.verify(userAchievementService, Mockito.times(1))
                .hasAchievement(achievementSaved.getId(), commentEventDto.getAuthorId());
    }
}