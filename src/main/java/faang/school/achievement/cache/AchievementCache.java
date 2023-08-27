package faang.school.achievement.cache;

import faang.school.achievement.model.Achievement;
import faang.school.achievement.repository.AchievementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

//this is stub
@Component
@RequiredArgsConstructor
public class AchievementCache {

    private final AchievementRepository achievementRepository;

    public Optional<Achievement> get(String title) {
        return achievementRepository.findByTitle(title);
    }
}
