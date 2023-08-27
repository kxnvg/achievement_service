package faang.school.achievement.filters.Achievement;

import faang.school.achievement.dto.achievement.DtoAchievement;
import faang.school.achievement.dto.achievement.DtoFilterAchievement;
import faang.school.achievement.model.Achievement;
import org.springframework.stereotype.Component;

import java.util.stream.Stream;

@Component
public class FilterAchievementName implements AchievementFilter {

    @Override
    public boolean isApplicable(DtoFilterAchievement dtoFilterAchievement) {
        return dtoFilterAchievement.getTitle() != null;
    }

    @Override
    public Stream<DtoAchievement> apply(Stream<DtoAchievement> achievements, DtoFilterAchievement filters) {
        return achievements.filter(achievement -> achievement.getTitle().equals(filters.getTitle()));
    }
}
