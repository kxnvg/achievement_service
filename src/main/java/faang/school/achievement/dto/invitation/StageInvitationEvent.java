package faang.school.achievement.dto.invitation;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class StageInvitationEvent {
    private Long authorId;
    private Long invitedId;
    private Long projectId;
    private LocalDateTime timestamp;
}