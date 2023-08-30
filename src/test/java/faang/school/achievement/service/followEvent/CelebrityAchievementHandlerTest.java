package faang.school.achievement.service.followEvent;

import faang.school.achievement.dto.follow.FollowEventDto;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.repository.cache.AchievementInMemCache;
import faang.school.achievement.service.handler.AchievementService;
import faang.school.achievement.service.handler.followHandler.CelebrityAchievementHandler;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class CelebrityAchievementHandlerTest {
    @Mock
    private AchievementService achievementService;
    @Mock
    private AchievementInMemCache achievementCache;
    @InjectMocks
    private CelebrityAchievementHandler celebrityAchievementHandler;



    @Test
    void messageHandleTest(){
        Achievement achievement = new Achievement();
        var followEvent = FollowEventDto.builder().targetUserId(1L).build();
        Mockito.when(achievementCache.getAchievement(Mockito.any())).thenReturn(Optional.of(achievement));

        celebrityAchievementHandler.handle(followEvent);
        Mockito.verify(achievementService, Mockito.times(1)).updateAchievementProgress(followEvent.getTargetUserId(),achievement);
    }
}
