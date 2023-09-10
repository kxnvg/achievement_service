package faang.school.achievement.service;

import faang.school.achievement.model.Achievement;
import faang.school.achievement.model.AchievementProgress;
import faang.school.achievement.repository.AchievementProgressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AchievementProgressService {

    private final AchievementProgressRepository achievementProgressRepository;

    public AchievementProgress getProgress(long userId, long achievementId) {
        return achievementProgressRepository.findByUserIdAndAchievementId(userId, achievementId)
                .orElse(createProgressIfNecessary(AchievementProgress.builder()
                        .achievement(Achievement.builder().id(achievementId).build())
                        .userId(userId)
                        .currentPoints(0)
                        .build()));
    }

    @Transactional
    public AchievementProgress createProgressIfNecessary(AchievementProgress achievementProgress) {
        return achievementProgressRepository.save(achievementProgress);
    }

    @Transactional
    public AchievementProgress updateProgress(AchievementProgress achievementProgress) {
        return achievementProgressRepository.save(achievementProgress);
    }
}