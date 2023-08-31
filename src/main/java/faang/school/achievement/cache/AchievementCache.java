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
    private Map<String, Achievement> achievementMap;

    public Achievement getAchievement(String achievementName){
        return achievementMap.get(achievementName);
    }

    @PostConstruct
    private void fillInAchievementCache(){
        achievementMap = achievementRepository.findAll().stream()
                .collect(Collectors.toMap(Achievement::getTitle, Function.identity()));
    }
}
