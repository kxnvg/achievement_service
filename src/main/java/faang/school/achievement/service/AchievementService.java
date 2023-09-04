package faang.school.achievement.service;

import faang.school.achievement.dto.AchievementDto;
import faang.school.achievement.dto.AchievementFilterDto;
import faang.school.achievement.dto.AchievementProgressDto;
import faang.school.achievement.dto.UserAchievementDto;
import faang.school.achievement.exception.DataValidationException;
import faang.school.achievement.mapper.AchievementMapper;
import faang.school.achievement.mapper.AchievementProgressMapper;
import faang.school.achievement.mapper.UserAchievementMapper;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.model.AchievementProgress;
import faang.school.achievement.model.UserAchievement;
import faang.school.achievement.repository.AchievementProgressRepository;
import faang.school.achievement.repository.AchievementRepository;
import faang.school.achievement.repository.UserAchievementRepository;
import faang.school.achievement.service.filter.AchievementFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class AchievementService {
    private final AchievementRepository achievementRepository;
    private final AchievementProgressRepository achievementProgressRepository;
    private final UserAchievementRepository userAchievementRepository;
    private final AchievementMapper achievementMapper;
    private final UserAchievementMapper userAchievementMapper;
    private final AchievementProgressMapper achievementProgressMapper;
    private final List<AchievementFilter> filters;

    public List<AchievementDto> getAllAchievements() {
        return toStream(achievementRepository.findAll())
                .map(achievementMapper::toDto)
                .toList();
    }

    public List<AchievementDto> getAllAchievements(AchievementFilterDto filterDto) {
        Stream<Achievement> achievementStream = toStream(achievementRepository.findAll());
        return filters.stream().filter(filter -> filter.isApplicable(filterDto))
                .flatMap(filter -> filter.apply(achievementStream, filterDto))
                .map(achievementMapper::toDto)
                .toList();
    }

    public AchievementDto getAchievementById(Long achievementId) {
        return achievementMapper.toDto(findAchievementById(achievementId));
    }

    public List<UserAchievementDto> getUserAchievements(Long userId) {
        List<UserAchievement> userAchievements = userAchievementRepository.findByUserId(userId);
        return userAchievements.stream().map(userAchievementMapper::toDto).toList();
    }

    public List<AchievementProgressDto> getUserAchievementProgresses(Long userId) {
        List<AchievementProgress> achievementProgresses = achievementProgressRepository.findByUserId(userId);
        return achievementProgresses.stream()
                .map(achievementProgressMapper::toDto)
                .toList();
    }

    private Achievement findAchievementById(Long achievementId) {
        return achievementRepository.findById(achievementId)
                .orElseThrow(() -> new DataValidationException("Achievement by ID: " + achievementId + " not found"));
    }

    private Stream<Achievement> toStream(Iterable<Achievement> achievementIterable){
        return StreamSupport.stream(achievementIterable.spliterator(), false);
    }
}
