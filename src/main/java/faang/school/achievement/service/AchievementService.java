package faang.school.achievement.service;

import faang.school.achievement.dto.AchievementDto;
import faang.school.achievement.dto.AchievementFilterDto;
import faang.school.achievement.dto.AchievementProgressDto;
import faang.school.achievement.mapper.AchievementMapper;
import faang.school.achievement.dto.event.AchievementEvent;
import faang.school.achievement.exception.EntityNotFoundException;
import faang.school.achievement.mapper.AchievementProgressMapper;
import faang.school.achievement.messaging.AchievementPublisher;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.model.AchievementProgress;
import faang.school.achievement.model.UserAchievement;
import faang.school.achievement.repository.AchievementProgressRepository;
import faang.school.achievement.repository.AchievementRepository;
import faang.school.achievement.repository.UserAchievementRepository;
import faang.school.achievement.service.filter.Filter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class AchievementService {

    private final AchievementPublisher achievementPublisher;
    private final AchievementMapper achievementMapper;
    private final AchievementProgressMapper achievementProgressMapper;
    private final AchievementProgressRepository achievementProgressRepository;
    private final UserAchievementRepository userAchievementRepository;
    private final AchievementRepository achievementRepository;
    private final List<Filter> filters;

    @Transactional(readOnly = true)
    public boolean hasAchievement(long userId, long achievementId) {
        return userAchievementRepository.existsByUserIdAndAchievementId(userId, achievementId);
    }

    @Transactional(readOnly = true)
    public AchievementDto getAchievement(long id) {
        Achievement achievement = achievementRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Not found", "There's no achievement with id " + id));
        return achievementMapper.toDto(achievement);
    }

    @Transactional(readOnly = true)
    public List<AchievementDto> getAchievements(AchievementFilterDto filter) {
        Stream<Achievement> achievements = StreamSupport.stream(achievementRepository.findAll().spliterator(), false);
        filters.stream()
                .filter(f -> f.isApplicable(filter))
                .forEach(f -> f.applyFilter(achievements, filter));
        return achievements
                .skip((long) filter.getPage() * filter.getPageSize())
                .limit(filter.getPageSize())
                .map(achievementMapper::toDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<AchievementDto> getUserAchievements(long userId) {
        return userAchievementRepository.findByUserId(userId)
                .stream()
                .map(userAchievement -> achievementMapper.toDto(userAchievement.getAchievement()))
                .toList();
    }

    @Transactional(readOnly = true)
    public List<AchievementProgressDto> getUserProgress(long userId) {
        return achievementProgressRepository.findByUserId(userId)
                .stream()
                .map(progress -> {
                    AchievementProgressDto dto = achievementProgressMapper.toDto(progress);
                    dto.setAchievement(achievementMapper.toDto(progress.getAchievement()));
                    return dto;
                }).toList();
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
