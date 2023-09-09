package faang.school.achievement.service;

import faang.school.achievement.model.AchievementProgress;
import faang.school.achievement.repository.AchievementProgressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AchievementProgressService {

    private final AchievementProgressRepository achievementProgressRepository;

    public Optional<AchievementProgress> getProgress(long userId, long achievementId) {
        return achievementProgressRepository.findByUserIdAndAchievementId(userId, achievementId);
    }

    @Transactional
    public AchievementProgress createProgress(AchievementProgress achievementProgress) {
        return achievementProgressRepository.save(achievementProgress);
    }

    @Transactional
    public AchievementProgress updateProgress(AchievementProgress achievementProgress) {
        return achievementProgressRepository.save(achievementProgress);
    }
}
