package faang.school.achievement.mapper;

import faang.school.achievement.dto.achievement.DtoAchievement;
import faang.school.achievement.model.Achievement;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AchievementMapper {
    AchievementMapper INSTANCE = Mappers.getMapper(AchievementMapper.class);

    Achievement dtoToAchievement(DtoAchievement dtoAchievement);

    DtoAchievement achievementToDto(Achievement achievement);
}
