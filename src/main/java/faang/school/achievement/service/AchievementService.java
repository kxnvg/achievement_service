package faang.school.achievement.service;

import faang.school.achievement.cache.AchievementCache;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.model.AchievementProgress;
import faang.school.achievement.model.UserAchievement;
import faang.school.achievement.repository.AchievementProgressRepository;
import faang.school.achievement.repository.AchievementRepository;
import faang.school.achievement.repository.UserAchievementRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AchievementService {

    private final AchievementRepository achievementRepository;
    private final AchievementProgressRepository achievementProgressRepository;
    private final UserAchievementRepository userAchievementRepository;
    private final AchievementCache achievementCache;

    @Transactional(readOnly = true)
    public Long getNumberOfCommentsForUser(Long userId, Long achievementId) {
        return achievementProgressRepository.findByUserIdAndAchievementId(userId, achievementId)
                .orElseThrow(() -> new EntityNotFoundException("Wrong user ID or achievement"))
                .getAchievement().getPoints();
    }

    @Transactional
    public Long addPoint(Long userId, Long achievementId) {
        AchievementProgress achievementProgress =
                achievementProgressRepository.findByUserIdAndAchievementId(userId, achievementId)
                .orElseThrow(() -> new EntityNotFoundException("Wrong user ID or achievement"));
        achievementProgress.increment();
        achievementProgressRepository.save(achievementProgress);
        return achievementProgress.getCurrentPoints();
    }

    @Transactional(readOnly = true)
    public boolean checkHasUserAchievement(Long userId, Long achievementId){
        return userAchievementRepository.existsByUserIdAndAchievementId(userId, achievementId);
    }

    @Transactional(readOnly = true)
    public Long getGoalForAchievement(String achievementTitle){
        return getAchievement(achievementTitle).getPoints();
    }

    @Transactional(readOnly = true)
    public Achievement getAchievement(String achievementTitle){
        return achievementCache.getAchievement(achievementTitle);
    }

    @Transactional
    public UserAchievement addAchievementForUser(Long userId, Achievement achievement){
        return userAchievementRepository.save(UserAchievement.builder().achievement(achievement).userId(userId).build());
    }

    @Transactional
    public void createProgress(Long userId, Long achievementId){
        achievementProgressRepository.createProgressIfNecessary(userId, achievementId);
    }
}
