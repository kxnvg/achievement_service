package faang.school.achievement.service;

import faang.school.achievement.exception.EntityNotFoundException;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.model.AchievementProgress;
import faang.school.achievement.model.Rarity;
import faang.school.achievement.repository.AchievementProgressRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.text.MessageFormat;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class AchievementProgressServiceTest {

    @Mock
    private AchievementProgressRepository achievementProgressRepository;
    @InjectMocks
    private AchievementProgressService achievementProgressService;

    private AchievementProgress achievementProgress;
    private Long userId;
    private Long achievementId;

    @BeforeEach
    void setUp() {
        String title = "Such a Sweetheart";

        Achievement achievement = Achievement.builder()
                .id(1L)
                .title(title)
                .rarity(Rarity.RARE)
                .points(10)
                .build();

        achievementProgress = AchievementProgress.builder()
                .id(1L)
                .achievement(achievement)
                .userId(1L)
                .currentPoints(0)
                .version(1L)
                .build();

        userId = achievementProgress.getUserId();
        achievementId = achievement.getId();
    }

    @Test
    void testSave() {
        achievementProgressService.save(achievementProgress);

        Mockito.verify(achievementProgressRepository, Mockito.times(1)).save(achievementProgress);
    }

    @Test
    void testFindByUserIdAndAchievementId() {
        achievementProgressService.createProgressIfNotExist(userId, achievementId);

        Mockito.verify(achievementProgressRepository, Mockito.times(1))
                .createProgressIfNecessary(userId, achievementId);
    }

    @Test
    void testGetByUserIdAndAchievementId_ReturnsProgress() {
        Mockito.when(achievementProgressRepository.findByUserIdAndAchievementId(userId, achievementId))
                .thenReturn(Optional.of(achievementProgress));

        AchievementProgress result = achievementProgressService.getByUserIdAndAchievementId(userId, achievementId);

        assertNotNull(result);
        assertEquals(achievementProgress, result);
    }

    @Test
    void testGetByUserIdAndAchievementId_ThrowsEntityNotFoundException() {
        Mockito.when(achievementProgressRepository.findByUserIdAndAchievementId(userId, achievementId))
                .thenReturn(Optional.empty());

        String expectedMessage = MessageFormat.format("UserId {0} and AchievementId {1}", userId, achievementId);

        Exception exception = assertThrows(EntityNotFoundException.class, () ->
                achievementProgressService.getByUserIdAndAchievementId(userId, achievementId)
        );

        assertTrue(exception.getMessage().contains(expectedMessage));
    }

    @Test
    void incrementProgress() {
        achievementProgressService.incrementProgress(achievementProgress);

        Mockito.verify(achievementProgressRepository, Mockito.times(1)).save(achievementProgress);
    }
}
