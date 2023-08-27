package faang.school.achievement.mapper;

import faang.school.achievement.dto.achievement.DtoAchievementProgress;
import faang.school.achievement.model.AchievementProgress;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AchievementProgressMapper {
    AchievementProgressMapper INSTANCE = Mappers.getMapper(AchievementProgressMapper.class);

    AchievementProgress dtoToAchievementProgress(DtoAchievementProgress achievementProgress);

    DtoAchievementProgress achievementProgressToDto(AchievementProgress achievementProgress);
}
