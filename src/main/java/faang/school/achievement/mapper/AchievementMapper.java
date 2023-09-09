package faang.school.achievement.mapper;

import faang.school.achievement.dto.AchievementDto;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.model.AchievementProgress;
import faang.school.achievement.model.UserAchievement;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import java.util.Collections;
import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AchievementMapper {

    @Mapping(source = "userAchievements", target = "userAchievementIds", qualifiedByName = "userAchievementsToIds")
    @Mapping(source = "progresses", target = "progressIds", qualifiedByName = "progressesToIds")
    AchievementDto toDto(Achievement achievement);

    @Mapping(source = "userAchievementIds", target = "userAchievements", qualifiedByName = "idsToUserAchievements")
    @Mapping(source = "progressIds", target = "progresses", qualifiedByName = "idsToAchievementProgresses")
    Achievement toEntity(AchievementDto achievementDto);

    @Named("userAchievementsToIds")
    default List<Long> userAchievementsToIds(List<UserAchievement> userAchievements) {
        return userAchievements != null ? userAchievements.stream()
                .map(UserAchievement::getId)
                .toList() : Collections.emptyList();
    }

    @Named("progressesToIds")
    default List<Long> progressesToIds(List<AchievementProgress> achievementProgresses) {
        return achievementProgresses != null ? achievementProgresses.stream()
                .map(AchievementProgress::getId)
                .toList() : Collections.emptyList();
    }

    @Named("idsToUserAchievements")
    default List<UserAchievement> idsToUserAchievements(List<Long> ids) {
        return ids != null ? ids.stream()
                .map(id -> {
                    return UserAchievement.builder()
                            .id(id)
                            .build();
                }).toList() : Collections.emptyList();
    }

    @Named("idsToAchievementProgresses")
    default List<AchievementProgress> idsToAchievementProgresses(List<Long> ids) {
        return ids != null ? ids.stream()
                .map(id -> {
                    return AchievementProgress.builder()
                            .id(id)
                            .build();
                }).toList() : Collections.emptyList();
    }


}
