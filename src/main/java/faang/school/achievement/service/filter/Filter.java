package faang.school.achievement.service.filter;



import faang.school.achievement.dto.AchievementFilterDto;
import faang.school.achievement.model.Achievement;

import java.util.stream.Stream;

public abstract class Filter {

    public Stream<Achievement> applyFilter(Stream<Achievement> achievements, AchievementFilterDto filter) {
        return achievements.filter(achievement -> applyFilter(achievement, filter));
    }

    public abstract boolean isApplicable(AchievementFilterDto filter);

    protected abstract boolean applyFilter(Achievement achievement, AchievementFilterDto filter);
}