package faang.school.achievement.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DtoMentorshipStartEvent {
    private Long requesterId;
    private Long receiverId;
}
