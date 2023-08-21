package faang.school.achievement.cache;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import faang.school.achievement.model.Achievement;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class AchievementCache {

    private final RedisTemplate<String, String> redisTemplate;
    private final ObjectMapper objectMapper;
    @PersistenceContext
    private EntityManager entityManager;

    @PostConstruct
    public void initCache() {

        String hql = "SELECT DISTINCT a FROM Achievement a " +
                "LEFT JOIN FETCH a.userAchievements ua " +
                "LEFT JOIN FETCH a.progresses p ";
        TypedQuery<Achievement> query = entityManager.createQuery(hql, Achievement.class);
        List<Achievement> achievements = query.getResultList();

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
