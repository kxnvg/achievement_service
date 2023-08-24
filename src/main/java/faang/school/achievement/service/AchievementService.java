package faang.school.achievement.service;

import faang.school.achievement.model.Achievement;
import faang.school.achievement.model.AchievementProgress;
import faang.school.achievement.model.UserAchievement;
import faang.school.achievement.repository.AchievementProgressRepository;
import faang.school.achievement.repository.UserAchievementRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Slf4j
public class AchievementService {
    private final UserAchievementRepository userAchievementRepository;
    private final AchievementProgressRepository achievementProgressRepository;

    public boolean hasAchievement(long userId, long achievementId) {
        return userAchievementRepository.existsByUserIdAndAchievementId(userId, achievementId);
    }

    @Transactional
    public void createAchievementProgress(long receiverId, Achievement achievement) {

        achievementProgressRepository.createProgressIfNecessary(receiverId, achievement.getId());
        var achievementProgress = achievementProgressRepository.findByUserIdAndAchievementId(receiverId, achievement.getId())
                .orElseThrow(() -> new RuntimeException("There is not user and achievement!"));
        achievementProgress.increment();

        if (achievementProgress.getCurrentPoints() >= achievement.getPoints()) {
            UserAchievement userAchievement = UserAchievement.builder().userId(1L).achievement(achievement).build();
            userAchievementRepository.save(userAchievement);
            log.info("User with id: " + receiverId + " has received achievement: " + achievement.getTitle());
        }
    }

    public long getProgress(long userId, long achievementId) {
        AchievementProgress achievementProgress = achievementProgressRepository.findByUserIdAndAchievementId(userId, achievementId)
                .orElseThrow(() -> new RuntimeException("There is no user and achievement progress!"));

        return achievementProgress.getCurrentPoints();
    }

}
