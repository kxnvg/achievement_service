package faang.school.achievement.mapper;

import faang.school.achievement.dto.event.AchievementEvent;
import faang.school.achievement.model.Achievement;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.FIELD, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AchievementMapper {

    AchievementEvent toEvent(Achievement entity);
}
