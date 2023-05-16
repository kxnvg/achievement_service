package faang.school.achievement.dto.event;

import lombok.Data;

@Data
public class FollowerEvent {
    private long followerId;
    private long followeeId;
}
