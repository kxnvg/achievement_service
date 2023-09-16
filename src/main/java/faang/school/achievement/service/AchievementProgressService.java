package faang.school.achievement.service;

import faang.school.achievement.exception.EntityNotFoundException;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.model.AchievementProgress;
import faang.school.achievement.repository.AchievementProgressRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.MessageFormat;

@Slf4j
@Service
@RequiredArgsConstructor
public class AchievementProgressService {
    private final AchievementProgressRepository achievementProgressRepository;

    @Transactional
    public void save(AchievementProgress achievementProgress) {
        achievementProgressRepository.save(achievementProgress);
    }

    @Transactional
    public AchievementProgress ensureUserAchievementProgress(Long userId, Achievement achievement) {
        log.info("Ensuring achievement progress for User: {} and Achievement: {}", userId, achievement.getTitle());
        Long achievementId = achievement.getId();

        if (!userHasProgress(userId, achievementId)) {
            AchievementProgress progress = AchievementProgress.builder()
                    .userId(userId)
                    .achievement(achievement)
                    .build();

            save(progress);
        }

        log.info("Achievement progress ensured for User: {} and Achievement: {}", userId, achievement.getTitle());
        return findByUserIdAndAchievementId(userId, achievementId);
    }

    @Transactional(readOnly = true)
    public AchievementProgress findByUserIdAndAchievementId(Long userId, Long achievementId) {
        return achievementProgressRepository.findByUserIdAndAchievementId(userId, achievementId)
                .orElseThrow(() -> {
                    String value = MessageFormat.format("UserId {0} and AchievementId {1}", userId, achievementId);
                    log.error("Achievement progress with '{}' not found.", value);
                    return new EntityNotFoundException("Achievement progress", value);
                });
    }

    @Transactional(readOnly = true)
    public boolean userHasProgress(Long userId, Long achievementId) {
        return achievementProgressRepository.existsByUserIdAndAchievementId(userId, achievementId);
    }

    @Transactional
    public void incrementProgress(AchievementProgress achievementProgress) {
        achievementProgress.increment();
        achievementProgressRepository.save(achievementProgress);
    }
}
