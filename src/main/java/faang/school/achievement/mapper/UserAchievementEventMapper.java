package faang.school.achievement.mapper;

import faang.school.achievement.dto.publish_event.DtoUserEventAchievement;
import faang.school.achievement.model.UserAchievement;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserAchievementEventMapper {
    UserAchievementEventMapper INSTANCE = Mappers.getMapper(UserAchievementEventMapper.class);

    DtoUserEventAchievement userAchievementToDto(UserAchievement userAchievement);
}
