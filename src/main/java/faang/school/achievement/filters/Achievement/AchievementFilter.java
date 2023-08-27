package faang.school.achievement.filters.Achievement;

import faang.school.achievement.dto.achievement.DtoAchievement;
import faang.school.achievement.dto.achievement.DtoFilterAchievement;

import java.util.stream.Stream;

public interface AchievementFilter {
    boolean isApplicable(DtoFilterAchievement dtoFilterAchievement);

    Stream<DtoAchievement> apply(Stream<DtoAchievement> achievements, DtoFilterAchievement filters);
}
