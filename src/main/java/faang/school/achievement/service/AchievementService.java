package faang.school.achievement.service;

import faang.school.achievement.dto.AchievementEventDto;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.model.AchievementProgress;
import faang.school.achievement.model.UserAchievement;
import faang.school.achievement.publisher.AchievementPublisher;
import faang.school.achievement.repository.AchievementProgressRepository;
import faang.school.achievement.repository.AchievementRepository;
import faang.school.achievement.repository.UserAchievementRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class AchievementService {

    private final AchievementPublisher achievementPublisher;
    private final AchievementProgressRepository progressRepository;
    private final AchievementRepository achievementRepository;
    private final UserAchievementRepository userAchievementRepository;

    public boolean hasAchievement(long authorId, long achievementId) {
        return userAchievementRepository.existsByUserIdAndAchievementId(authorId, achievementId);
    }

    public void createProgressIfNecessary(long achievementId, long userId) {
        progressRepository.createProgressIfNecessary(userId, achievementId);
    }

    public long getProgress(long authorId, long achievementId) {
        AchievementProgress progress = progressRepository.findByUserIdAndAchievementId(authorId, achievementId).orElseThrow();
        progress.increment();
        log.info("Achievement progress for authorId:{} has incremented successfully", authorId);
        progressRepository.save(progress);
        return progress.getCurrentPoints();
    }

    public UserAchievement giveAchievement(long authorId, long achievementId) {
        Achievement cuttentAchievement = achievementRepository.findById(achievementId).orElseThrow();
        UserAchievement userAchievement = UserAchievement.builder()
                .userId(authorId)
                .achievement(cuttentAchievement)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        userAchievementRepository.save(userAchievement);
        achievementPublisher.publish(new AchievementEventDto(authorId, cuttentAchievement.getTitle(), LocalDateTime.now()));
        log.info("Achievement:{} for authorId:{} saved successfully", cuttentAchievement.getTitle(), authorId);
        return userAchievement;
    }
}
