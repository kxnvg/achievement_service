package faang.school.achievement.dto;

import faang.school.achievement.messaging.EventType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MentorshipStartEvent {
    private Long mentorId;
    private Long menteeId;
    private EventType eventType;
    private LocalDateTime receivedAt;
}