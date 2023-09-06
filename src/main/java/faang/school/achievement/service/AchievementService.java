package faang.school.achievement.service;

import faang.school.achievement.model.Achievement;
import faang.school.achievement.repository.AchievementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AchievementService {

    private final AchievementCache achievementCache;
    private final AchievementRepository achievementRepository;

    @Transactional
    public Achievement createAchievement(Achievement achievement) {
        return achievementRepository.save(achievement);
    }

    public Achievement getAchievement(String title) {
        return achievementCache.getAchievement(title);
    }
}
