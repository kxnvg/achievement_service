package faang.school.achievement.service;

import faang.school.achievement.model.Achievement;
import faang.school.achievement.model.UserAchievement;
import faang.school.achievement.repository.AchievementProgressRepository;
import faang.school.achievement.repository.AchievementRepository;
import faang.school.achievement.repository.UserAchievementRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AchievementService {
    private final AchievementRepository achievementRepository;
    private final UserAchievementRepository userAchievementRepository;
    private final AchievementProgressRepository achievementProgressRepository;

    @Transactional
    public void updateAchievementProgress(long userId, Achievement achievement) {
        if (userAchievementRepository.existsByUserIdAndAchievementId(userId, achievement.getId())) return;

        achievementProgressRepository.createProgressIfNecessary(userId, achievement.getId());
        var progress = achievementProgressRepository.findByUserIdAndAchievementId(userId, achievement.getId()).get();
        progress.increment();

        if (progress.getCurrentPoints() >= achievement.getPoints()) {
            UserAchievement userAchievement = UserAchievement.builder().userId(userId).achievement(achievement).build();
            userAchievementRepository.save(userAchievement);
            log.info("User with id: " + userId + " has received achievement: " + achievement.getTitle());
        }
    }
}
