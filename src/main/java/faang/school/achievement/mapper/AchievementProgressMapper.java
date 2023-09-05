package faang.school.achievement.mapper;

import faang.school.achievement.dto.AchievementProgressDto;
import faang.school.achievement.model.AchievementProgress;
import org.mapstruct.Mapping;

public interface AchievementProgressMapper {
    @Mapping(source = "achievement.id", target = "achievementId")
    AchievementProgressDto toDto(AchievementProgress achievementProgress);

    @Mapping(source = "achievementId", target = "achievement.id")
    AchievementProgress toEntity(AchievementProgressDto achievementProgressDto);
}
