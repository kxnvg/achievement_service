package faang.school.achievement.cache;

import faang.school.achievement.exception.EntityNotFoundException;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.repository.AchievementRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class AchievementCache {

    private static final Map<String, Achievement> achievements = new HashMap<>();
    private final AchievementRepository achievementRepository;

    @PostConstruct
    public void init() {
        Iterable<Achievement> list = achievementRepository.findAll();
        for (Achievement achievement : list) {
            achievements.put(achievement.getTitle(), achievement);
        }
    }

    public Achievement getAchievement(String title) {
        if (achievements.containsKey(title)) {
            return achievements.get(title);
        }
        throw new EntityNotFoundException("Achievement not found");
    }
}
