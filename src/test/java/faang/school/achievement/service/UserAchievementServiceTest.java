package faang.school.achievement.service;

import faang.school.achievement.model.Achievement;
import faang.school.achievement.model.Rarity;
import faang.school.achievement.model.UserAchievement;
import faang.school.achievement.repository.UserAchievementRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UserAchievementServiceTest {

    @Mock
    private UserAchievementRepository userAchievementRepository;
    @InjectMocks
    private UserAchievementService userAchievementService;

    private UserAchievement userAchievement;
    private long userId;
    private long achievementId;

    @BeforeEach
    void setUp() {
        String title = "Such a Sweetheart";

        Achievement achievement = Achievement.builder()
                .id(1L)
                .title(title)
                .rarity(Rarity.RARE)
                .points(10)
                .build();

        userAchievement = UserAchievement.builder()
                .userId(1L)
                .achievement(achievement)
                .build();

        userId = userAchievement.getUserId();
        achievementId = achievement.getId();
    }

    @Test
    void testSave() {
        userAchievementService.save(userAchievement);

        Mockito.verify(userAchievementRepository, Mockito.times(1)).save(userAchievement);
    }

    @Test
    void testGiveUserAchievementIfNecessary() {
        userAchievementService.createUserAchievementIfNecessary(userId, achievementId);

        Mockito.verify(userAchievementRepository, Mockito.times(1))
                .createUserAchievementIfNecessary(userId, achievementId);
    }

    @Test
    void testUserHasAchievement() {
        userAchievementService.userHasAchievement(userId, achievementId);

        Mockito.verify(userAchievementRepository, Mockito.times(1))
                .existsByUserIdAndAchievementId(userId, achievementId);
    }
}
