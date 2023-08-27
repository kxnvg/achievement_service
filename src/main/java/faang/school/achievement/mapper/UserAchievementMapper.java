package faang.school.achievement.mapper;

import faang.school.achievement.dto.achievement.DtoUserAchievement;
import faang.school.achievement.model.UserAchievement;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserAchievementMapper {
    UserAchievementMapper INSTANCE = Mappers.getMapper(UserAchievementMapper.class);

    UserAchievement dtoToUserAchievement(DtoUserAchievement dtoUserAchievement);

    DtoUserAchievement userAchievementToDto(UserAchievement userAchievement);
}
