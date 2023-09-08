package faang.school.achievement.cache;

import faang.school.achievement.model.Achievement;
import faang.school.achievement.repository.AchievementRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Scope("singleton")
public class AchievementCache {
    private final AchievementRepository achievementRepository;
    private Map<String, Achievement> achievements;

    public Achievement get(String title) {
        return achievements.get(title);
    }

    @PostConstruct
    private void initAchievements() {
        achievements = achievementRepository.findAll().stream()
                .collect(Collectors.toUnmodifiableMap(Achievement::getTitle, Function.identity()));
    }
}
