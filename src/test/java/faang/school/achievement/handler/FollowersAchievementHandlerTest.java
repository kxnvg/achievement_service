package faang.school.achievement.handler;

import faang.school.achievement.cache.AchievementCache;
import faang.school.achievement.dto.FollowerEventDto;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.model.UserAchievement;
import faang.school.achievement.service.AchievementService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FollowersAchievementHandlerTest {
    @Mock
    private AchievementService achievementService;
    @Mock
    private AchievementCache achievementCache;

    private FollowersAchievementHandler followersAchievementHandler;

    private Achievement achievement;

    private LocalDateTime currentTime;

    private FollowerEventDto followerEventDto;

    private final String ACHIEVEMENT_NAME = "subscribers";

    @BeforeEach
    void setUp() {
        followersAchievementHandler = new FollowersAchievementHandler(achievementService, achievementCache);
        followersAchievementHandler.setFollowersAchievementName(ACHIEVEMENT_NAME);
        UserAchievement firstUserAchievement = UserAchievement.builder()
                .id(1)
                .userId(1)
                .build();
        UserAchievement secondUserAchievement = UserAchievement.builder()
                .id(2)
                .userId(2)
                .build();
        achievement = Achievement.builder()
                .id(1)
                .points(100)
                .userAchievements(List.of(firstUserAchievement, secondUserAchievement))
                .build();
        currentTime = LocalDateTime.now();
        followerEventDto = FollowerEventDto.builder()
                .followerId(2)
                .followeeId(1)
                .subscriptionTime(currentTime)
                .build();
    }

    @Test
    void handleThrowExceptionTest() {
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> followersAchievementHandler.handle(followerEventDto));

        assertEquals("There is no achievement named: subscribers", exception.getMessage());
    }

    @Test
    void handleTest() {
        when(achievementCache.get(ACHIEVEMENT_NAME))
                .thenReturn(Optional.empty());
        when(achievementService.getAchievement(ACHIEVEMENT_NAME))
                .thenReturn(Optional.of(achievement));
        when(achievementService.hasAchievement(1, 1))
                .thenReturn(false);
        when(achievementService.getProgress(1, 1))
                .thenReturn(100L);

        followersAchievementHandler.handle(followerEventDto);

        verify(achievementCache).get(ACHIEVEMENT_NAME);
        verify(achievementService).getAchievement(ACHIEVEMENT_NAME);
        verify(achievementService).hasAchievement(1, 1);
        verify(achievementService).checkAndCreateAchievementProgress(1, 1);
        verify(achievementService).getProgress(1, 1);
        verify(achievementService).giveAchievement(1, 1);
    }
}
