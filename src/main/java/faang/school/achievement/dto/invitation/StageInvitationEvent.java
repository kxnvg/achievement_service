package faang.school.achievement.dto.invitation;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class StageInvitationEvent {
    Long authorId;
    Long invitedId;
    Long projectId;
    LocalDateTime timestamp;
}