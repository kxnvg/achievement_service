package faang.school.achievement.handler;

//import faang.school.achievement.achievement_test.AchievementCache;
import faang.school.achievement.dto.FollowerEventDto;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.model.AchievementProgress;
import faang.school.achievement.model.UserAchievement;
import faang.school.achievement.service.AchievementService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

//@ExtendWith(MockitoExtension.class)
//class FollowersAchievementHandlerTest {
//    @Mock
//    private AchievementService achievementService;
//    @Mock
//    private AchievementCache achievementCache;
//
//    private FollowersAchievementHandler followersAchievementHandler;
//
//    private Achievement achievement;
//    private AchievementProgress achievementProgress;
//    private LocalDateTime currentTime;
//    private FollowerEventDto followerEventDto;
//
//    @BeforeEach
//    void setUp() {
//        followersAchievementHandler = new FollowersAchievementHandler(achievementService, achievementCache);
//        followersAchievementHandler.setFollowersAchievementName("subscribers");
//        UserAchievement firstUserAchievement = UserAchievement.builder()
//                .id(1)
//                .userId(1)
//                .build();
//        UserAchievement secondUserAchievement = UserAchievement.builder()
//                .id(2)
//                .userId(2)
//                .build();
//        achievement = Achievement.builder()
//                .id(1)
//                .points(100)
//                .userAchievements(List.of(firstUserAchievement, secondUserAchievement))
//                .build();
//        achievementProgress = AchievementProgress.builder()
//                .id(1)
//                .userId(1)
//                .achievement(achievement)
//                .currentPoints(99)
//                .build();
//        currentTime = LocalDateTime.now();
//        followerEventDto = FollowerEventDto.builder()
//                .followerId(2)
//                .followeeId(1)
//                .subscriptionTime(currentTime)
//                .build();
//    }
//
//    @Test
//    void handleTest() {
//        when(achievementCache.get("subscribers"))
//                .thenReturn(achievement);
//        when(achievementService.hasAchievement(achievement, 1))
//                .thenReturn(false);
//        when(achievementService.getUserProgressByAchievementAndUserId(1, 1))
//                .thenReturn(achievementProgress);
//
//        followersAchievementHandler.handle(followerEventDto);
//
//        assertEquals(100, achievementProgress.getCurrentPoints());
//
//        verify(achievementCache).get("subscribers");
//        verify(achievementService).hasAchievement(achievement, 1);
//        verify(achievementService).getUserProgressByAchievementAndUserId(1, 1);
//        verify(achievementService).addAchievementToUserIfEnoughPoints(achievementProgress, achievement, 1);
//    }
//}
