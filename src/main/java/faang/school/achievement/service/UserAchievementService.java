package faang.school.achievement.service;

import faang.school.achievement.model.UserAchievement;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserAchievementService {
    private final UserAchievementService userAchievementService;
    public boolean hasAchievement(Long userId,Long achievementId) {
        return userAchievementService.hasAchievement(userId, achievementId);
    }

    public void giveAchievement(UserAchievement userAchievement) {

    }
}
