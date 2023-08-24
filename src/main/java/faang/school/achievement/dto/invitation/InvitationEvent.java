package faang.school.achievement.dto.invitation;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class InvitationEvent {
    Long senderId;
    Long receiverId;
    Long projectId;
    LocalDateTime timestamp;
}