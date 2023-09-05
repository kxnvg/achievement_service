package faang.school.achievement.cache;

import faang.school.achievement.model.Achievement;
import faang.school.achievement.repository.AchievementRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.assertEquals;


@ExtendWith(value = {MockitoExtension.class})
public class AchievementCacheTest {

    @Mock
    private AchievementRepository achievementRepository;

    @InjectMocks
    private AchievementCache achievementCache;

    private List<Achievement> achievements;

    @BeforeEach
    void init() {
        achievements = List.of(
                Achievement.builder()
                        .id(1L)
                        .title("destroyer")
                        .build(),
                Achievement.builder()
                        .id(2L)
                        .title("saint")
                        .build());
    }

    @Test
    public void testInit() {
        when(achievementRepository.findAll()).thenReturn(achievements);
        achievementCache.init();
        verify(achievementRepository).findAll();
    }

    @Test
    public void testGet() {
        Achievement achievement = Achievement.builder()
                .id(1L)
                .title("destroyer")
                .build();
        when(achievementRepository.findAll()).thenReturn(achievements);
        achievementCache.init();
        var result = achievementCache.get("destroyer");
        assertEquals(achievement, result);
    }

    @Test
    public void testGetAll() {
        var expectedList = List.of(
                Achievement.builder()
                        .id(1L)
                        .title("destroyer")
                        .build(),
                Achievement.builder()
                        .id(2L)
                        .title("saint")
                        .build());
        when(achievementRepository.findAll()).thenReturn(achievements);
        achievementCache.init();
        var result = achievementCache.getAll();
        var list = result.stream().sorted(Comparator.comparingLong(Achievement::getId)).toList();
        assertEquals(expectedList, list);
    }
}
