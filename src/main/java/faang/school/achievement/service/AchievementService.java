package faang.school.achievement.service;

import faang.school.achievement.cache.AchievementCache;
import faang.school.achievement.exception.EntityNotFoundException;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.repository.AchievementRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class AchievementService {
    private final AchievementCache achievementCache;
    private final AchievementRepository achievementRepository;

    @Transactional(readOnly = true)
    public Achievement getAchievementByTitle(String title) {
        return achievementRepository.getAchievementByTitle(title)
                .orElseThrow(() -> {
                    log.error("Achievement with title '{}' not found.", title);
                    return new EntityNotFoundException("Achievement", title);
                });
    }

    @Transactional(readOnly = true)
    public Achievement getAchievementFromCache(String title) {
        return achievementCache.getAchievement(title);
    }
}
