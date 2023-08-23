package faang.school.achievement.service;

import faang.school.achievement.dto.AchievementEventDto;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.model.AchievementProgress;
import faang.school.achievement.model.UserAchievement;
import faang.school.achievement.publisher.AchievementEventPublisher;
import faang.school.achievement.repository.AchievementProgressRepository;
import faang.school.achievement.repository.UserAchievementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AchievementService {

    private final AchievementProgressRepository achievementProgressRepository;
    private final UserAchievementRepository userAchievementRepository;
    private final AchievementEventPublisher achievementEventPublisher;

    public void giveAchievement(Achievement achievement, long userId) {
        UserAchievement userAchievement = UserAchievement.builder()
                .achievement(achievement)
                .userId(userId)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        userAchievementRepository.save(userAchievement);
        achievementEventPublisher.publishMessage(AchievementEventDto.builder()
                .achievementId(achievement.getId())
                .receiverId(userId)
                .achievedAt(LocalDateTime.now())
                .build());
    }

    public void addAchievementToUserIfEnoughPoints(AchievementProgress achievementProgress, Achievement achievement, long userId) {
        long progressPoints = achievementProgress.getCurrentPoints();
        long neededPoints = achievement.getPoints();

        if (progressPoints >= neededPoints) {
            giveAchievement(achievement, userId);
        }
    }

    public AchievementProgress getUserProgressByAchievementAndUserId(Achievement achievement, long userId) {
        long achievementId = achievement.getId();

        Optional<AchievementProgress> userProgress = achievementProgressRepository.findByUserIdAndAchievementId(userId, achievementId);
        AchievementProgress userAchievementProgress;

        if (userProgress.isPresent()) {
            userAchievementProgress = userProgress.get();
        } else {
            achievementProgressRepository.createProgressIfNecessary(userId, achievementId);
            userAchievementProgress = getProgress(userId, achievementId).get();
        }
        return userAchievementProgress;
    }

    public boolean hasAchievement(Achievement achievement, long userId) {
        return achievement.getUserAchievements().stream()
                .anyMatch(userAchievement -> userAchievement.getUserId() == userId);
    }

    private Optional<AchievementProgress> getProgress(long userId, long achievementId) {
        return achievementProgressRepository.findByUserIdAndAchievementId(userId, achievementId);
    }
}
