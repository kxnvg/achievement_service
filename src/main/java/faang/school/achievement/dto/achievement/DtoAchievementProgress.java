package faang.school.achievement.dto.achievement;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DtoAchievementProgress {
    private DtoAchievement achievement;
    private long userId;
    private long currentPoints;
    private long version;
}
