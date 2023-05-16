package faang.school.achievement.service;

import faang.school.achievement.model.Achievement;
import faang.school.achievement.repository.AchievementRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class AchievementHolder {

    private static final Map<String, Achievement> ACHIEVEMENTS = new HashMap<>();

    private final AchievementRepository achievementRepository;

    @PostConstruct
    public void init() {
        achievementRepository.findAll()
                .forEach(achievement -> ACHIEVEMENTS.put(achievement.getTitle(), achievement));
    }

    public Achievement getAchievement(String title) {
        return ACHIEVEMENTS.get(title);
    }
}
