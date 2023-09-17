package faang.school.achievement.cache;

import faang.school.achievement.exception.AchievementNotFoundException;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.repository.AchievementRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class AchievementCache {
    private final AchievementRepository achievementRepository;
    private static Map<String, Achievement> achievementMap;

    public Achievement getAchievement(String achievementName) {
        Achievement achievement = achievementMap.get(achievementName);

        if (achievement == null) {
            log.error("Achievement not found in cache for title: {}", achievementName);
            throw new AchievementNotFoundException(achievementName);
        }

        return achievement;
    }

    @PostConstruct
    private void init() {
        achievementMap = achievementRepository.findAll().stream()
                .collect(Collectors.toUnmodifiableMap(Achievement::getTitle, Function.identity()));
    }
}
