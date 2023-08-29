package faang.school.achievement.dto.achievement;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DtoUserAchievement {
    private long id;
    private DtoAchievement achievement;
    private long userId;
}
