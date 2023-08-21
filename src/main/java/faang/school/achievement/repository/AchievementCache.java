package faang.school.achievement.repository;

import faang.school.achievement.model.Achievement;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;


@Component
@RequiredArgsConstructor
public class AchievementCache {
    private final AchievementRepository achievementRepository;
    private Map<String, Achievement> achievements;

    @PostConstruct
    public void initCache() {
        achievements = achievementRepository.findAll().stream()
                .collect(Collectors.toMap(Achievement::getTitle, Function.identity()));
    }

    public Achievement getAchievement(String title) {
        return achievements.get(title);
    }
}
