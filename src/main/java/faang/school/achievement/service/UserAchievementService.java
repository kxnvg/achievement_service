package faang.school.achievement.service;

import faang.school.achievement.model.UserAchievement;
import faang.school.achievement.repository.UserAchievementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserAchievementService {
    private final UserAchievementRepository userAchievementRepository;
    public boolean hasAchievement(Long userId,Long achievementId) {
        return userAchievementRepository.existsByUserIdAndAchievementId(userId, achievementId);
    }

    public void giveAchievement(UserAchievement userAchievement) {

    }

    public void createProgressIfNecessary(long userId, long achievementId){
        userAchievementRepository.createUserAchievementIfNecessary(userId, achievementId);
    }
}
