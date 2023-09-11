package faang.school.achievement.dto.redis;

import faang.school.achievement.messaging.EventType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MentorshipStartEvent implements Serializable {
    private Long mentorId;
    private Long menteeId;
    private EventType eventType;
    private LocalDateTime receivedAt;
}