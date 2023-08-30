package faang.school.achievement.cache;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.repository.AchievementRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AchievementCache {

    private final RedisTemplate<String, String> redisCacheTemplate;
    private final ObjectMapper objectMapper;
    private final AchievementRepository achievementRepository;

    @PostConstruct
    public void initCache() {

        Iterable<Achievement> achievements = achievementRepository.findAll();
        for (Achievement achievement : achievements) {
            try {
                redisCacheTemplate.opsForValue().set(achievement.getTitle(), objectMapper.writeValueAsString(achievement));
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public Optional<Achievement> get(String title) {
        try {
            String achievementJson = redisCacheTemplate.opsForValue().get(title);
            Achievement achievement = objectMapper.readValue(achievementJson, Achievement.class);
            return Optional.of(achievement);
        } catch (JsonProcessingException e) {
            return Optional.empty();
        }
    }
}
