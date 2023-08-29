package faang.school.achievement.service;

import faang.school.achievement.config.context.UserContext;
import faang.school.achievement.dto.achievement.DtoAchievement;
import faang.school.achievement.dto.achievement.DtoAchievementProgress;
import faang.school.achievement.dto.achievement.DtoFilterAchievement;
import faang.school.achievement.filters.Achievement.AchievementFilter;
import faang.school.achievement.mapper.AchievementMapper;
import faang.school.achievement.mapper.AchievementProgressMapper;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.model.AchievementProgress;
import faang.school.achievement.model.UserAchievement;
import faang.school.achievement.repository.AchievementProgressRepository;
import faang.school.achievement.repository.AchievementRepository;
import faang.school.achievement.repository.UserAchievementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AchievementService {
    private final UserContext userContext;
    private final AchievementRepository achievementRepository;
    private final UserAchievementRepository userAchievementRepository;
    private final AchievementProgressRepository achievementProgressRepository;
    private final AchievementProgressMapper achievementProgressMapper = AchievementProgressMapper.INSTANCE;
    private final AchievementMapper achievementMapper = AchievementMapper.INSTANCE;
    private final List<AchievementFilter> achievementFilters;
    private final List<DtoAchievement> dtoAchievements;

    public List<DtoAchievement> allAchievements(DtoFilterAchievement filters) {
        dtoAchievements.clear();
        achievementRepository.findAll().forEach(achievement -> dtoAchievements.add(achievementMapper.achievementToDto(achievement)));
        return achievementFilters.stream().filter(filter -> filter.isApplicable(filters)).flatMap(filter -> filter.apply(dtoAchievements.stream(), filters)).toList();
    }

    public List<DtoAchievement> userAchievement() {
        long id = userContext.getUserId();
        return userAchievementRepository.findByUserId(id).stream().map(userAchievement -> achievementMapper.achievementToDto(userAchievement.getAchievement())).toList();
    }

    public DtoAchievement getAchievement(long id) {
        return achievementMapper.achievementToDto(achievementRepository.findById(id).orElseThrow(() -> new RuntimeException("achievement with id " + id + " not found")));
    }

    public List<DtoAchievementProgress> unearnedAchievements() {
        long userId = userContext.getUserId();
        return achievementProgressRepository.findByUserId(userId).stream().map(achievementProgressMapper::achievementProgressToDto).toList();
    }

    @Transactional(readOnly = true)
    public boolean hasAchievement(long userId, long achievementId) {
        return userAchievementRepository.existsByUserIdAndAchievementId(userId, achievementId);
    }

    @Transactional
    public void incrementProgress(AchievementProgress achievementProgress) {
        achievementProgress.increment();
        achievementProgressRepository.save(achievementProgress);
    }

    @Transactional
    public void giveAchievement(long userId, Achievement achievement) {
        userAchievementRepository.save(UserAchievement.builder().achievement(achievement).userId(userId).build());
    }

    @Transactional
    public AchievementProgress getAchievementProgress(long userId, long achievementId) {
        return achievementProgressRepository.findByUserIdAndAchievementId(userId, achievementId)
                .orElseGet(() -> achievementProgressRepository.createProgressIfNecessary(userId, achievementId));
    }
}

