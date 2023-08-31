package faang.school.achievement.handler;

import faang.school.achievement.cache.AchievementCache;
import faang.school.achievement.dto.EventPostDto;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.service.AchievementService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.Mockito.*;
//
//@ExtendWith(MockitoExtension.class)
//public class OpinionLeaderAchievementHandlerTest {
//
//    @Mock
//    private AchievementService achievementService;
//    @Mock
//    private AchievementCache achievementCache;
//    @InjectMocks
//    private OpinionLeaderAchievementHandler achievementHandler;
//
//    private final String ACHIEVEMENT_TITTLE = "Opinion leader";
//    private final long ACHIEVEMENT_ID = 1L;
//    private final long AUTHOR_ID = 1L;
//    private final long ACHIEVEMENT_POINTS = 1000L;
//    private Achievement achievement;
//    private EventPostDto postDto;
//
//    @BeforeEach
//    void initData() {
//        achievement = Achievement.builder()
//                .id(ACHIEVEMENT_ID)
//                .points(ACHIEVEMENT_POINTS)
//                .build();
//        postDto = EventPostDto.builder()
//                .authorId(AUTHOR_ID)
//                .postId(1L)
//                .build();
//    }
//
//    @Test
//    void testHandle() {
//        when(achievementCache.get(ACHIEVEMENT_TITTLE)).thenReturn(Optional.ofNullable(achievement));
//        when(achievementService.hasAchievement(AUTHOR_ID, ACHIEVEMENT_ID)).thenReturn(true);
//        when(achievementService.getProgress(AUTHOR_ID, ACHIEVEMENT_ID)).thenReturn(ACHIEVEMENT_POINTS);
//
//        achievementHandler.handle(postDto);
//        verify(achievementService).giveAchievement(AUTHOR_ID, ACHIEVEMENT_ID);
//    }
//
//    @Test
//    void testHandleWithCreateProgress() {
//        when(achievementCache.get(ACHIEVEMENT_TITTLE)).thenReturn(Optional.ofNullable(achievement));
//        when(achievementService.hasAchievement(AUTHOR_ID, ACHIEVEMENT_ID)).thenReturn(false);
//        when(achievementService.getProgress(AUTHOR_ID, ACHIEVEMENT_ID)).thenReturn(ACHIEVEMENT_POINTS);
//
//        achievementHandler.handle(postDto);
//        verify(achievementService).giveAchievement(AUTHOR_ID, ACHIEVEMENT_ID);
//        verify(achievementService).createProgressIfNecessary(ACHIEVEMENT_ID, AUTHOR_ID);
//    }
//}
