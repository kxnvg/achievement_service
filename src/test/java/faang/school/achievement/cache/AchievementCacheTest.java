package faang.school.achievement.cache;

import faang.school.achievement.model.Achievement;
import faang.school.achievement.repository.AchievementRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.assertEquals;


@ExtendWith(value = {MockitoExtension.class})
public class AchievementCacheTest {

    @Mock
    private AchievementRepository achievementRepository;

    @Mock
    private Map<String, Achievement> achievements;

    @InjectMocks
    private AchievementCache achievementCache;

    @Test
    public void testInit() {
        Achievement achievement1 = Achievement.builder()
                .id(1L)
                .title("destroyer")
                .build();

        Achievement achievement2 = Achievement.builder()
                .id(2L)
                .title("saint")
                .build();
        when(achievementRepository.findAll()).thenReturn(List.of(achievement1, achievement2));
        achievementCache.init();
        verify(achievementRepository).findAll();
        verify(achievements).put("destroyer", achievement1);
        verify(achievements).put("saint", achievement2);
    }

    @Test
    public void testGet() {
        Achievement achievement = Achievement.builder()
                .id(1L)
                .title("destroyer")
                .build();
        when(achievements.get("destroyer")).thenReturn(achievement);
        achievementCache.init();
        var result = achievementCache.get("destroyer");
        assertEquals(achievement, result);
    }

}
