package faang.school.achievement.service;

import faang.school.achievement.dto.AchievementDto;
import faang.school.achievement.dto.AchievementEventDto;
import faang.school.achievement.dto.AchievementProgressDto;
import faang.school.achievement.dto.UserAchievementDto;
import faang.school.achievement.mapper.AchievementMapper;
import faang.school.achievement.mapper.AchievementProgressMapper;
import faang.school.achievement.mapper.UserAchievementMapper;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.model.AchievementProgress;
import faang.school.achievement.model.UserAchievement;
import faang.school.achievement.publisher.AchievementEventPublisher;
import faang.school.achievement.repository.AchievementProgressRepository;
import faang.school.achievement.repository.AchievementRepository;
import faang.school.achievement.repository.UserAchievementRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AchievementService {

    private final AchievementProgressRepository achievementProgressRepository;
    private final UserAchievementRepository userAchievementRepository;
    private final AchievementRepository achievementRepository;
    private final AchievementEventPublisher achievementEventPublisher;
    private final AchievementProgressMapper achievementProgressMapper;
    private final AchievementMapper achievementMapper;
    private final UserAchievementMapper userAchievementMapper;

    public AchievementDto getAchievementById(long id) {
        Achievement achievement = achievementRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("There is no achievement with id: %d", id)));
        return achievementMapper.toAchievementDto(achievement);
    }

    public Page<Achievement> getAllAchievements(Pageable pageable) {
        return achievementRepository.findAll(pageable);
    }

    public List<UserAchievementDto> getUserAchievements(long userId) {
        List<UserAchievement> achievements = userAchievementRepository.findByUserId(userId);
        return userAchievementMapper.toDtoList(achievements);
    }

    public List<AchievementProgressDto> getAllAchievementProgressByUserId(long userId) {
        List<AchievementProgress> achievementProgresses = achievementProgressRepository.findByUserId(userId);
        return achievementProgressMapper.toDtoList(achievementProgresses);
    }

    public AchievementProgressDto getAchievementProgressByUserId(long achievementId, long userId) {
        AchievementProgress achievementProgress = getUserProgressByAchievementAndUserId(achievementId, userId);
        return achievementProgressMapper.toDto(achievementProgress);
    }


    @Transactional
    public void giveAchievement(Achievement achievement, long userId) {
        UserAchievement userAchievement = UserAchievement.builder()
                .achievement(achievement)
                .userId(userId)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        userAchievementRepository.save(userAchievement);
        achievementEventPublisher.publishMessage(AchievementEventDto.builder()
                .achievementId(achievement.getId())
                .receiverId(userId)
                .achievedAt(LocalDateTime.now())
                .build());
    }

    public void addAchievementToUserIfEnoughPoints(AchievementProgress achievementProgress, Achievement achievement, long userId) {
        long progressPoints = achievementProgress.getCurrentPoints();
        long neededPoints = achievement.getPoints();

        if (progressPoints >= neededPoints) {
            giveAchievement(achievement, userId);
        }
    }

    public AchievementProgress getUserProgressByAchievementAndUserId(long achievementId, long userId) {
        Optional<AchievementProgress> userProgress = achievementProgressRepository.findByUserIdAndAchievementId(userId, achievementId);
        AchievementProgress userAchievementProgress;

        if (userProgress.isPresent()) {
            userAchievementProgress = userProgress.get();
        } else {
            achievementProgressRepository.createProgressIfNecessary(userId, achievementId);
            userAchievementProgress = getProgress(userId, achievementId).get();
        }
        return userAchievementProgress;
    }

    public boolean hasAchievement(Achievement achievement, long userId) {
        return achievement.getUserAchievements().stream()
                .anyMatch(userAchievement -> userAchievement.getUserId() == userId);
    }

    private Optional<AchievementProgress> getProgress(long userId, long achievementId) {
        return achievementProgressRepository.findByUserIdAndAchievementId(userId, achievementId);
    }
}
