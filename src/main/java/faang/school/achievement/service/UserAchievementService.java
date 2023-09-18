package faang.school.achievement.service;

import faang.school.achievement.model.UserAchievement;
import faang.school.achievement.repository.UserAchievementRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserAchievementService {
    private final UserAchievementRepository userAchievementRepository;

    @Transactional
    public void save(UserAchievement userAchievement) {
        userAchievementRepository.save(userAchievement);
    }

    @Transactional
    public void createUserAchievementIfNecessary(long userId, long achievementId) {
        userAchievementRepository.createUserAchievementIfNecessary(userId, achievementId);
    }

    @Transactional(readOnly = true)
    public boolean userHasAchievement(Long userId, Long achievementId) {
        return userAchievementRepository.existsByUserIdAndAchievementId(userId, achievementId);
    }
}
