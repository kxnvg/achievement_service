package faang.school.achievement.service;

import faang.school.achievement.mapper.AchievementMapper;
import faang.school.achievement.dto.event.AchievementEvent;
import faang.school.achievement.exception.EntityNotFoundException;
import faang.school.achievement.messaging.AchievementPublisher;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.model.AchievementProgress;
import faang.school.achievement.model.UserAchievement;
import faang.school.achievement.repository.AchievementProgressRepository;
import faang.school.achievement.repository.UserAchievementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AchievementService {

    private final AchievementPublisher achievementPublisher;
    private final AchievementMapper achievementMapper;
    private final AchievementProgressRepository achievementProgressRepository;
    private final UserAchievementRepository userAchievementRepository;

    @Transactional(readOnly = true)
    public boolean hasAchievement(long userId, long achievementId) {
        return userAchievementRepository.existsByUserIdAndAchievementId(userId, achievementId);
    }

    @Transactional(readOnly = true)
    public AchievementProgress getProgress(long userId, long achievementId) {
        return achievementProgressRepository.findByUserIdAndAchievementId(userId, achievementId)
                .orElseThrow(() -> new EntityNotFoundException("Not found", "There's no achievement progress for user " + userId + " and achievement " + achievementId));
    }

    @Transactional
    public void createProgressIfNecessary(long userId, long achievementId) {
        achievementProgressRepository.createProgressIfNecessary(userId, achievementId);
    }

    @Transactional
    public void giveAchievement(long userId, Achievement achievement) {
        UserAchievement userAchievement = new UserAchievement();
        userAchievement.setUserId(userId);
        userAchievement.setAchievement(achievement);
        userAchievementRepository.save(userAchievement);
        AchievementEvent event = achievementMapper.toEvent(achievement);
        event.setUserId(userId);
        achievementPublisher.publish(event);
    }
}
