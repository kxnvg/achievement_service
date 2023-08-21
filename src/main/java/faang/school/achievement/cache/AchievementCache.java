package faang.school.achievement.cache;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.repository.AchievementRepository;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AchievementCache {

    private final RedisTemplate<String, String> redisTemplate;
    private final ObjectMapper objectMapper;
    private final AchievementRepository achievementRepository;
    @PersistenceContext
    private EntityManager entityManager;

    @PostConstruct
    public void initCache() {

        Iterable<Achievement> achievements = achievementRepository.findAll();
        for (Achievement achievement : achievements) {
            try {
                redisTemplate.opsForValue().set(achievement.getTitle(), objectMapper.writeValueAsString(achievement));
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public Achievement get(String title) {
        try {
            return objectMapper.readValue(redisTemplate.opsForValue().get(title), Achievement.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
