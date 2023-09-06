package faang.school.achievement.service;


import faang.school.achievement.dto.AchievementProgressDto;
import faang.school.achievement.mapper.AchievementProgressMapper;
import faang.school.achievement.model.AchievementProgress;
import faang.school.achievement.repository.AchievementProgressRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AchievementProgressService {

    private final AchievementProgressMapper achievementProgressMapper;
    private final AchievementProgressRepository progressRepository;

    public AchievementProgressDto getAchievementProgressByUserId(long achievementId, long userId) {
        AchievementProgress achievementProgress = getUserProgressByAchievementAndUserId(achievementId, userId);
        return achievementProgressMapper.toDto(achievementProgress);
    }

    public List<AchievementProgressDto> getAchievementsProgressByUserId(long userId) {
        List<AchievementProgress> achievementProgresses = progressRepository.findByUserId(userId);
        return achievementProgressMapper.toDtoList(achievementProgresses);
    }

    private AchievementProgress getUserProgressByAchievementAndUserId(long achievementId, long userId) {
        return progressRepository.findByUserIdAndAchievementId(userId, achievementId)
                .orElseGet(() -> createAndGetAchievementProgress(achievementId, userId));
    }

    private AchievementProgress createAndGetAchievementProgress(long achievementId, long userId) {
        progressRepository.createProgressIfNecessary(userId, achievementId);
        log.info("Created new progress for achievementId: {} and userId: {}", achievementId, userId);
        return progressRepository.findByUserIdAndAchievementId(userId, achievementId).get();
    }
}
