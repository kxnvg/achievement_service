package faang.school.achievement.service.filter;

import faang.school.achievement.dto.AchievementFilterDto;
import faang.school.achievement.model.Achievement;
import org.springframework.stereotype.Component;

@Component
public class TitleFilter extends Filter {

    @Override
    protected boolean isApplicable(AchievementFilterDto filter) {
        return filter.getTitlePattern() != null && !filter.getTitlePattern().isBlank();
    }

    @Override
    protected boolean applyFilter(Achievement achievement, AchievementFilterDto filter) {
        return achievement.getTitle().contains(filter.getTitlePattern());
    }
}