package faang.school.achievement.dto.publish_event;

import faang.school.achievement.dto.achievement.DtoAchievement;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DtoUserEventAchievement implements DtoEvent {
    private long userId;
    private DtoAchievement achievement;
}
