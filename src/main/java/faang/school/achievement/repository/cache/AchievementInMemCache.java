package faang.school.achievement.repository.cache;

import faang.school.achievement.model.Achievement;
import faang.school.achievement.repository.AchievementRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;


@Component
@RequiredArgsConstructor
public class AchievementInMemCache implements AchievementCache{
    private final AchievementRepository achievementRepository;
    private Map<String, Achievement> achievements;

    @PostConstruct
    public void initCache() {
        achievements = achievementRepository.findAll().stream()
                .collect(Collectors.toMap(Achievement::getTitle, Function.identity()));
    }

    public Optional<Achievement> getAchievement(String title) {
        return Optional.of(achievements.get(title))
                .or(() -> {
                    Optional<Achievement> optionalAchievement = achievementRepository.findByTitle(title);
                    optionalAchievement.ifPresent(achievement -> achievements.put(title, achievement));

                    return optionalAchievement;
                });
    }
}
