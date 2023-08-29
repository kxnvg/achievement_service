package faang.school.achievement.filters.Achievement;

import faang.school.achievement.dto.achievement.DtoAchievement;
import faang.school.achievement.dto.achievement.DtoFilterAchievement;
import org.springframework.stereotype.Component;

import java.util.stream.Stream;

@Component
public class FilterAchievementDescription implements AchievementFilter {
    @Override
    public boolean isApplicable(DtoFilterAchievement dtoFilterAchievement) {
        return dtoFilterAchievement.getDescription() != null;
    }

    @Override
    public Stream<DtoAchievement> apply(Stream<DtoAchievement> achievements, DtoFilterAchievement filters) {
        return achievements.filter(description -> description.getDescription().toLowerCase().indexOf(filters.getDescription().toLowerCase()) != -1);
    }
}
